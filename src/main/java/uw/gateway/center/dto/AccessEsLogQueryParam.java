package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 网关访问日志查询参数。
 */
@Schema(title = "网关访问日志查询参数", description = "网关访问日志查询参数")
public class AccessEsLogQueryParam extends AuthPageQueryParam {
    /**
     * 应用信息
     */
    @QueryMeta(expr = "appInfo like ?")
    @Schema(title = "应用信息", description = "应用信息")
    private String appInfo;
    /**
     * 应用主机
     */
    @QueryMeta(expr = "appHost like ?")
    @Schema(title = "应用主机", description = "应用主机")
    private String appHost;
    /**
     * SaasId。
     */
    @QueryMeta(expr = "saasId=?")
    @Schema(title = "saasId", description = "saasId", hidden = true)
    private Long saasId;
    /**
     * 用户id
     */
    @QueryMeta(expr = "userId=?")
    @Schema(title = "用户id", description = "用户id")
    private Long userId;
    /**
     * 商户ID
     */
    @QueryMeta(expr = "mchId=?")
    @Schema(title = "商户ID", description = "商户ID")
    private Long mchId;
    /**
     * 用户类型
     */
    @QueryMeta(expr = "userType=?")
    @Schema(title = "用户类型", description = "用户类型")
    private Integer userType;

    /**
     * 操作人ip
     */
    @QueryMeta(expr = "userIp=?")
    @Schema(title = "操作人ip", description = "操作人ip")
    private String userIp;

    /**
     * 应用名。
     */
    @QueryMeta(expr = "serviceName=?")
    @Schema(title = "应用名", description = "应用名")
    private String serviceName;

    /**
     * 应用主机。
     */
    @QueryMeta(expr = "serviceHost=?")
    @Schema(title = "应用主机", description = "应用主机")
    private String serviceHost;

    /**
     * 请求ID。
     */
    @QueryMeta(expr = "requestId=?")
    @Schema(title = "请求ID", description = "请求ID")
    private String requestId;

    /**
     * 请求主机。
     */
    @QueryMeta(expr = "requestHost=?")
    @Schema(title = "请求主机", description = "请求主机")
    private String requestHost;

    /**
     * 访问路径。
     */
    @QueryMeta(expr = "requestPath=?")
    @Schema(title = "请求路径", description = "请求路径")
    private String requestPath;

    /**
     * 请求方法。
     */
    @QueryMeta(expr = "requestMethod=?")
    @Schema(title = "请求方法", description = "请求方法")
    private String requestMethod;

    /**
     * 请求大小。
     */
    @QueryMeta(expr = "requestSize between ? and ?")
    @Schema(title = "请求大小", description = "请求大小")
    private Long[] requestSizeRange;

    /**
     * 返回大小。
     */
    @QueryMeta(expr = "responseSize between ? and ?")
    @Schema(title = "返回大小", description = "返回大小")
    private Long[] responseSizeRange;

    /**
     * 请求毫秒数范围
     */
    @QueryMeta(expr = "responseMillis between ? and ?")
    @Schema(title = "请求毫秒数范围", description = "请求毫秒数范围")
    private Long[] responseMillisRange;

    /**
     * 响应状态码
     */
    @QueryMeta(expr = "responseCode=?")
    @Schema(title = "响应状态码", description = "响应状态码")
    private Integer responseCode;

    /**
     * 响应状态码范围
     */
    @QueryMeta(expr = "responseCode between ? and ?")
    @Schema(title = "响应状态码范围", description = "响应状态码范围")
    private Integer[] responseCodeRange;

    /**
     * 创建时间范围
     */
    @QueryMeta(expr = "\\\"@timestamp\\\" between ? and ?")
    @Schema(title = "创建时间范围", description = "创建时间范围")
    private Date[] requestDateRange;

    /**
     * 允许的排序属性。
     * key:排序名 value:排序字段
     *
     * @return
     */
    @Override
    public Map<String, String> ALLOWED_SORT_PROPERTY() {
        return new HashMap<>() {{
            put( "id", "id" );
            put("@timestamp","\\\"@timestamp\\\"");
        }};
    }

    public AccessEsLogQueryParam() {
        super();
    }

    public AccessEsLogQueryParam(Long saasId) {
        super(saasId);
    }

    public String getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    @Override
    public Long getSaasId() {
        return saasId;
    }

    @Override
    public void setSaasId(Long saasId) {
        this.saasId = saasId;
    }

    @Override
    public Long getMchId() {
        return mchId;
    }

    @Override
    public void setMchId(Long mchId) {
        this.mchId = mchId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Integer getUserType() {
        return userType;
    }

    @Override
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
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

    public Long[] getRequestSizeRange() {
        return requestSizeRange;
    }

    public void setRequestSizeRange(Long[] requestSizeRange) {
        this.requestSizeRange = requestSizeRange;
    }

    public Long[] getResponseSizeRange() {
        return responseSizeRange;
    }

    public void setResponseSizeRange(Long[] responseSizeRange) {
        this.responseSizeRange = responseSizeRange;
    }

    public Long[] getResponseMillisRange() {
        return responseMillisRange;
    }

    public void setResponseMillisRange(Long[] responseMillisRange) {
        this.responseMillisRange = responseMillisRange;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer[] getResponseCodeRange() {
        return responseCodeRange;
    }

    public void setResponseCodeRange(Integer[] responseCodeRange) {
        this.responseCodeRange = responseCodeRange;
    }

    public Date[] getRequestDateRange() {
        return requestDateRange;
    }

    public void setRequestDateRange(Date[] requestDateRange) {
        this.requestDateRange = requestDateRange;
    }
}