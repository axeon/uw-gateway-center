package uw.gateway.center.acl.filter.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.util.IpMatchUtils;

import java.util.List;

/**
 * IP访问控制信息。
 */
@Schema(title = "IP访问控制信息", description = "IP访问控制信息")
public class MscAclFilterInfo {

    /**
     * id
     */
    @Schema(title = "id", description = "id")
    private long id;

    /**
     * saasId
     */
    @Schema(title = "saasId", description = "saasId")
    private long saasId;

    /**
     * 用户ID
     */
    @Schema(title = "用户ID", description = "用户ID")
    private long userId;

    /**
     * 用户类型
     */
    @Schema(title = "用户类型", description = "用户类型")
    private int userType;

    /**
     * 过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式
     */
    @Schema(title = "过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式", description = "过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式")
    private int filterType;

    /**
     * 过滤器名
     */
    @Schema(title = "过滤器名", description = "过滤器名")
    private String filterName;

    /**
     * 数据列表。
     */
    private List<IpMatchUtils.IpRange> ipRangeList;

    public MscAclFilterInfo() {
    }

    public MscAclFilterInfo(long id, long saasId, long userId, int userType, int filterType, String filterName) {
        this.id = id;
        this.saasId = saasId;
        this.userId = userId;
        this.userType = userType;
        this.filterType = filterType;
        this.filterName = filterName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSaasId() {
        return saasId;
    }

    public void setSaasId(long saasId) {
        this.saasId = saasId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public List<IpMatchUtils.IpRange> getIpRangeList() {
        return ipRangeList;
    }

    public void setIpRangeList(List<IpMatchUtils.IpRange> ipRangeList) {
        this.ipRangeList = ipRangeList;
    }
}
