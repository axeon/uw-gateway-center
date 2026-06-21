package uw.gateway.center.acl.rate.limiter;

import uw.common.util.SystemClock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 单机固定窗口限速器，用于网关 ACL 流控。
 *
 * <p>以 {@code windowSizeInMillis} 为一个时间窗口，窗口内累计请求数不得超过 {@link #limitRequests}、
 * 累计字节数不得超过 {@link #limitBytes}。窗口边界对齐到系统时钟网格（{@code floor(now/windowSizeInMillis)}），
 * 超过窗口后计数归零，进入下一窗口。</p>
 *
 * <h3>两阶段限流</h3>
 * 网关场景下，请求进入时尚不知道实际流量，故采用两阶段：
 * <ul>
 *   <li>请求入口：{@link #tryAcquire(int, long)} 通常传 {@code bytes=0}，只检查并占用"请求数"配额；</li>
 *   <li>请求结束：{@link #addBytes(long)} 按实际请求/响应字节数回填"字节"配额。</li>
 * </ul>
 * <p>因此字节维度是"事后统计"——本请求不会被字节维度拦截，但会影响后续请求是否放行。</p>
 *
 * <h3>同步策略</h3>
 * <p>窗口重置与计数累加在 {@link ReentrantLock} 临界区内原子完成，避免"重置与累加交叉"导致的计数失真
 * 或限流失效。临界区为纯内存算术、持锁时间极短。</p>
 *
 * <h3>算法限制</h3>
 * <p>固定窗口在窗口边界处存在最多 2 倍突发。字节维度使用 {@code long} 以避免大流量溢出。
 * {@code windowSizeInMillis} 不建议超过约 24 天，否则返回值中的剩余毫秒会超出 {@code int} 范围溢出。</p>
 *
 * <h3>语义说明</h3>
 * <ul>
 *   <li>窗口边界对齐到墙钟网格（如 windowSize=1s 时窗口为整秒边界），所有实例的同类窗口边界一致。</li>
 *   <li>限流对象创建后首个窗口长度取决于创建时刻在网格中的位置（0 ~ windowSize 之间），属正常行为。</li>
 *   <li>窗口切换以 {@link #tryAcquire(int, long)} 为权威：发现窗口过期时归零所有计数。
 *   {@link #addBytes(long)} 不触发窗口重置，跨窗口回填的字节计入回填时刻所属窗口，存在轻微统计偏差，
 *   不影响限流正确性。</li>
 * </ul>
 *
 * @author axeon
 */
public class FixedWindowRateLimiter {

    /**
     * 时间窗口，毫秒级。
     */
    private final long windowSizeInMillis;

    /**
     * 最大请求数。
     */
    private final int limitRequests;

    /**
     * 最大流量字节数。
     */
    private final long limitBytes;

    /**
     * 保护窗口状态的锁。临界区为纯内存算术，持锁极短。
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 当前窗口编号 = floor(now / windowSizeInMillis)，初值 0 保证首次调用必然触发窗口初始化。
     */
    private long windowIndex = 0;

    /**
     * 当前窗口累计请求数（锁内读写，无需 volatile）。
     */
    private long windowRequests = 0;

    /**
     * 当前窗口累计字节数（锁内读写，无需 volatile）。
     */
    private long windowBytes = 0;

    /**
     * 构造函数。
     *
     * @param windowSizeInMillis 时间窗口，毫秒级。小于 1 表示不限流；建议不超过约 24 天以避免剩余毫秒 int 溢出
     * @param limitRequests      时间窗口内允许的最大请求数
     * @param limitBytes         时间窗口内允许的最大流量字节数
     */
    public FixedWindowRateLimiter(long windowSizeInMillis, int limitRequests, int limitBytes) {
        this.windowSizeInMillis = windowSizeInMillis;
        this.limitRequests = limitRequests;
        this.limitBytes = limitBytes;
    }

    /**
     * 尝试占用配额，是窗口切换的权威判定点。
     *
     * <p>请求维度立即检查并占用；字节维度仅在 {@code bytes > 0} 时预占（网关实际调用传 0，
     * 字节由 {@link #addBytes(long)} 事后回填）。每次调用先检测当前是否进入新窗口，是则归零所有计数。</p>
     *
     * @param requests 本次申请的请求数，必须 &gt;= 0
     * @param bytes    本次预占的字节数；传 &lt;= 0 表示不预占（事后用 {@link #addBytes(long)} 回填）
     * @return {@code null} 表示通过（未超限）；否则返回 {@code int[3]}：
     *         <ul>
     *           <li>[0] = 当前窗口剩余毫秒（大于 0）</li>
     *           <li>[1] = 超出请求数（windowRequests - limitRequests，未超时为负值）</li>
     *           <li>[2] = 超出字节数（windowBytes - limitBytes，未超时为负值）</li>
     *         </ul>
     *         任一维度超出（&gt; 0）即整体拒绝。
     */
    public int[] tryAcquire(int requests, long bytes) {
        // 时间窗口小于 1，不限制
        if (this.windowSizeInMillis < 1) {
            return null;
        }
        lock.lock();
        try {
            long now = SystemClock.now();
            long idx = now / windowSizeInMillis;
            // 窗口切换则归零全部计数（tryAcquire 是窗口切换的权威判定点）
            if (idx != windowIndex) {
                windowIndex = idx;
                windowRequests = 0;
                windowBytes = 0;
            }
            // 占用请求配额
            windowRequests += requests;
            // 占用字节配额（bytes<=0 时不预占，交由 addBytes 事后回填）
            if (bytes > 0) {
                windowBytes += bytes;
            }
            int overRequests = (int) (windowRequests - limitRequests);
            int overBytes = (int) (windowBytes - limitBytes);
            if (overRequests > 0 || overBytes > 0) {
                long remainMillis = windowSizeInMillis - (now - idx * windowSizeInMillis);
                return new int[]{(int) remainMillis, overRequests, overBytes};
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 事后回填实际字节数。
     *
     * <p>网关场景下，请求/响应的实际字节数只有在请求结束后才知道，因此请求入口的 {@link #tryAcquire}
     * 通常不预占字节，而在请求结束后调用本方法按真实流量回填，用于影响后续请求的字节维度判定。</p>
     *
     * <p>本方法<b>不触发窗口重置</b>（窗口切换由 {@link #tryAcquire} 统一负责），因此不会干扰请求维度计数。
     * 若回填发生时窗口已切换（由后续某次 {@code tryAcquire} 检测并重置），本次回填的字节将计入
     * {@code windowIndex} 所标记的窗口，存在轻微统计偏差，不影响限流正确性。</p>
     *
     * @param bytes 本次回填的字节数
     */
    public void addBytes(long bytes) {
        lock.lock();
        try {
            windowBytes += bytes;
        } finally {
            lock.unlock();
        }
    }
}
