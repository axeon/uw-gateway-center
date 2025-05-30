package uw.gateway.center.acl.filter.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * IP访问控制结果。
 */
@Schema(title = "IP访问控制结果", description = "IP访问控制结果")
public class MscAclFilterResult {

    /**
     * 默认通过的情况,提升性能。
     */
    public static final MscAclFilterResult ALLOWED = new MscAclFilterResult();

    /**
     * 请求的IP地址.
     */
    @Schema(title = "请求的IP地址", description = "请求的IP地址")
    private String ip;

    /**
     * 匹配到的过滤器信息.
     */
    @Schema(title = "匹配到的过滤器信息", description = "匹配到的过滤器信息")
    private MscAclFilterInfo filterInfo;

    /**
     * 是否允许访问.
     */
    @Schema(title = "是否允许访问", description = "是否允许访问")
    private boolean allowed = true;

    /**
     * 构造函数.
     *
     * @param ip
     * @param filterInfo
     */
    public MscAclFilterResult(String ip, MscAclFilterInfo filterInfo) {
        this.ip = ip;
        this.filterInfo = filterInfo;
        this.allowed = false;
    }

    /**
     * 构造函数.
     * @param ip
     * @param filterInfo
     * @param allowed
     */
    public MscAclFilterResult(String ip, MscAclFilterInfo filterInfo, boolean allowed) {
        this.ip = ip;
        this.filterInfo = filterInfo;
        this.allowed = allowed;
    }

    public MscAclFilterResult() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public MscAclFilterInfo getFilterInfo() {
        return filterInfo;
    }

    public void setFilterInfo(MscAclFilterInfo filterInfo) {
        this.filterInfo = filterInfo;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
