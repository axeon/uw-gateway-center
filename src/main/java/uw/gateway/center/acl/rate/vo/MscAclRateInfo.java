package uw.gateway.center.acl.rate.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * MscAclRate实体类
 * 系统流控配置
 *
 * @author axeon
 */
@Schema(title = "系统流控配置", description = "系统流控配置")
public class MscAclRateInfo implements Serializable {

    /**
     * id
     */
    @Schema(title = "id", description = "id")
    private long id;

    /**
     * saas等级。
     */
    @Schema(title = "saasLevel", description = "saasLevel")
    private long saasLevel;

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
     * 限速名称
     */
    @Schema(title = "限速名称", description = "限速名称")
    private String limitName;

    /**
     * 限速类型
     */
    @Schema(title = "限速类型", description = "限速类型")
    private int limitType;

    /**
     * 限速资源
     */
    @Schema(title = "限速资源", description = "限速资源")
    private String limitUri;

    /**
     * 限速秒数
     */
    @Schema(title = "限速秒数", description = "限速秒数")
    private int limitSeconds;

    /**
     * 限速请求
     */
    @Schema(title = "限速请求", description = "限速请求")
    private int limitRequests;

    /**
     * 限速字节数
     */
    @Schema(title = "限速字节数", description = "限速字节数")
    private int limitBytes;

    public MscAclRateInfo() {
    }

    public MscAclRateInfo(long id, long saasLevel, long saasId, long userId, int userType, String limitName, int limitType, String limitUri, int limitSeconds, int limitRequests, int limitBytes) {
        this.id = id;
        this.saasLevel = saasLevel;
        this.saasId = saasId;
        this.userId = userId;
        this.userType = userType;
        this.limitName = limitName;
        this.limitType = limitType;
        this.limitUri = limitUri;
        this.limitSeconds = limitSeconds;
        this.limitRequests = limitRequests;
        this.limitBytes = limitBytes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSaasLevel() {
        return saasLevel;
    }

    public void setSaasLevel(long saasLevel) {
        this.saasLevel = saasLevel;
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

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public int getLimitType() {
        return limitType;
    }

    public void setLimitType(int limitType) {
        this.limitType = limitType;
    }

    public String getLimitUri() {
        return limitUri;
    }

    public void setLimitUri(String limitUri) {
        this.limitUri = limitUri;
    }

    public int getLimitSeconds() {
        return limitSeconds;
    }

    public void setLimitSeconds(int limitSeconds) {
        this.limitSeconds = limitSeconds;
    }

    public int getLimitRequests() {
        return limitRequests;
    }

    public void setLimitRequests(int limitRequests) {
        this.limitRequests = limitRequests;
    }

    public int getLimitBytes() {
        return limitBytes;
    }

    public void setLimitBytes(int limitBytes) {
        this.limitBytes = limitBytes;
    }
}