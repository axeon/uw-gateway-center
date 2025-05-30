package uw.gateway.center.acl.rate.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 系统流控结果。
 */
@Schema(title = "系统流控结果", description = "系统流控结果")
public class MscAclRateResult {

    /**
     * 默认通过的情况,提升性能。
     */
    public static final MscAclRateResult ALLOWED = new MscAclRateResult();

    /**
     * 请求的IP地址.
     */
    @Schema(title = "请求的IP地址", description = "请求的IP地址")
    private String ip;

    /**
     * 是否允许访问.
     */
    @Schema(title = "是否允许访问", description = "是否允许访问")
    private boolean allowed = true;

    /**
     * 剩余的秒数.
     */
    @Schema(title = "剩余的秒数", description = "剩余的秒数")
    private int remainSeconds;

    /**
     * 超出的请求数.
     */
    @Schema(title = "超出的请求数", description = "超出的请求数")
    private int overRequests;

    /**
     * 超出的流量数.
     */
    @Schema(title = "超出的流量数", description = "超出的流量数")
    private int overBytes;

    /**
     * 匹配到的过滤器信息.
     */
    @Schema(title = "匹配到的过滤器信息", description = "匹配到的过滤器信息")
    private MscAclRateInfo rateInfo;

    public MscAclRateResult(String ip, MscAclRateInfo rateInfo, int[] limitResult) {
        this.ip = ip;
        this.rateInfo = rateInfo;
        if (limitResult != null && limitResult.length == 3) {
            this.allowed = false;
            this.remainSeconds = (int) Math.ceil( limitResult[0] / 1000f );
            this.overRequests = limitResult[1];
            this.overBytes = limitResult[2];
        }
    }

    public MscAclRateResult() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public MscAclRateInfo getRateInfo() {
        return rateInfo;
    }

    public void setRateInfo(MscAclRateInfo rateInfo) {
        this.rateInfo = rateInfo;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public int getOverRequests() {
        return overRequests;
    }

    public void setOverRequests(int overRequests) {
        this.overRequests = overRequests;
    }

    public int getOverBytes() {
        return overBytes;
    }

    public void setOverBytes(int overBytes) {
        this.overBytes = overBytes;
    }
}
