package uw.gateway.center.acl.rate.limiter;

import uw.common.util.SystemClock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 一个简单的固定窗口限速器。
 * 使用上要注意，不要设置过长的时间窗口。
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
    private final int limitBytes;

    /**
     * 当前窗口字节数。
     */
    private final AtomicInteger windowBytes = new AtomicInteger(0);

    /**
     * 当前窗口请求数。
     */
    private final AtomicInteger windowRequests = new AtomicInteger(0);

    /**
     * 当前窗口开始时间。
     */
    private final AtomicLong windowStartMillis = new AtomicLong(0);

    /**
     * 构造函数。
     *
     * @param limitMillis   时间窗口，毫秒级。
     * @param limitRequests 最大请求数。
     * @param limitBytes    最大流量字节数。
     */
    public FixedWindowRateLimiter(long limitMillis, int limitRequests, int limitBytes) {
        this.windowSizeInMillis = limitMillis;
        this.limitRequests = limitRequests;
        this.limitBytes = limitBytes;
    }

    /**
     * 尝试获取令牌。
     *
     * @return int[3]
     * 如果返回null，则说明通过了。
     * 否则返回int[3]，第一个元素为剩余时间，第二个元素为超出请求数，第三个元素为超出流量数。
     */
    public int[] tryAcquire(int requests, int bytes) {
        //如果时间窗口小于1，则不限制
        if (this.windowSizeInMillis < 1) {
            return null;
        }

        long current = SystemClock.now();
        long remainMillis = windowSizeInMillis - (current - windowStartMillis.get());
        if (remainMillis < 0) {
            windowStartMillis.set(current);
            windowRequests.set(0);
            windowBytes.set(0);
        }
        //检测请求数
        int overRequests = windowRequests.addAndGet(requests) - limitRequests;
        int overBytes;
        if (bytes > 0) {
            overBytes = windowBytes.addAndGet(bytes) - limitBytes;
        } else {
            overBytes = windowBytes.get() - limitBytes;
        }
        if (overRequests > 0 || overBytes > 0) {
            return new int[]{(int) remainMillis, overRequests, overBytes};
        } else {
            return null;
        }
    }

    /**
     * 递增bytes。
     * 因为拦截之后才能真正计入流量，所以需要单独调用。
     *
     * @param bytes
     */
    public void addBytes(int bytes) {
        windowBytes.addAndGet(bytes);
    }
}
