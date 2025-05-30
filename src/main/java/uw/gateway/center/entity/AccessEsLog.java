package uw.gateway.center.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.dao.annotation.TableMeta;
import uw.log.es.vo.LogBaseVo;

/**
 * 网关访问日志。
 */
@Schema(title = "网关访问日志", description = "网关访问日志")
@TableMeta(tableName = "\\\"uw.gateway.access.log\\\"")
public class AccessEsLog extends LogBaseVo {

    /**
     * App信息。
     */
    @Schema(title = "App信息", description = "App信息" )
    private String appInfo;

    /**
     * App主机。
     */
    @Schema(title = "App主机", description = "App主机" )
    private String appHost;

    /**
     * saasId。
     */
    @Schema(title = "saasId", description = "saasId")
    private long saasId = -1;

    /**
     * 用户Id。
     */
    @Schema(title = "用户Id", description = "用户Id")
    private long userId = -1;

    /**
     * 用户类型
     */
    @Schema(title = "用户类型", description = "用户类型")
    private int userType = -1;

    /**
     * 用户IP。
     */
    @Schema(title = "用户IP", description = "用户IP")
    private String userIp;

    /**
     * 用户代理。
     */
    @Schema(title = "用户代理", description = "用户代理")
    private String userAgent;

    /**
     * 应用名。
     */
    @Schema(title =  "应用名", description = "应用名")
    private String serviceName;

    /**
     * 应用主机。
     */
    @Schema(title = "应用主机", description = "应用主机")
    private String serviceHost;

    /**
     * 请求ID。
     */
    @Schema(title = "请求ID", description = "请求ID")
    private String requestId;

    /**
     * 请求主机。
     */
    @Schema(title = "请求主机", description = "请求主机")
    private String requestHost;

    /**
     * 访问路径。
     */
    @Schema(title = "请求路径", description = "请求路径")
    private String requestPath;

    /**
     * 请求方法。
     */
    @Schema(title = "请求方法", description = "请求方法")
    private String requestMethod;

    /**
     * 请求大小。
     */
    @Schema(title = "请求大小", description = "请求大小")
    private long requestSize;

    /**
     * 请求日期。
     */
    @Schema(title = "请求日期", description = "请求日期")
    private long requestDate;

    /**
     * 返回代码。
     */
    @Schema(title = "返回代码", description = "返回代码")
    private int responseCode;

    /**
     * 返回大小。
     */
    @Schema(title = "返回大小", description = "返回大小")
    private long responseSize;

    /**
     * 返回毫秒数。
     */
    @Schema(title = "返回毫秒数", description = "返回毫秒数")
    private long responseMillis;


    public AccessEsLog() {
    }

    @Override
    public String getAppInfo() {
        return appInfo;
    }

    @Override
    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    @Override
    public String getAppHost() {
        return appHost;
    }

    @Override
    public void setAppHost(String appHost) {
        this.appHost = appHost;
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

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestHost() {
        return requestHost;
    }

    public void setRequestHost(String requestHost) {
        this.requestHost = requestHost;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public long getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(long requestSize) {
        this.requestSize = requestSize;
    }

    public long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(long requestDate) {
        this.requestDate = requestDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    public long getResponseMillis() {
        return responseMillis;
    }

    public void setResponseMillis(long responseMillis) {
        this.responseMillis = responseMillis;
    }
}
