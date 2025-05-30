package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* saas访问统计列表查询参数。
*/
@Schema(title = "saas访问统计列表查询参数", description = "saas访问统计列表查询参数")
public class AccessSaasStatsQueryParam extends AuthPageQueryParam{

    public AccessSaasStatsQueryParam() {
        super();
    }

    public AccessSaasStatsQueryParam(Long saasId) {
        super(saasId);
    }
	
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
            put( "saasId", "saas_id" );
            put( "statsDate", "stats_date" );
            put( "createDate", "create_date" );
            put( "userNum", "user_num" );
            put( "ipNum", "ip_num" );
            put( "sessionNum", "session_num" );
            put( "serviceNum", "service_num" );
            put( "apiNum", "api_num" );
            put( "requestNum", "request_num" );
            put( "requestSize", "request_size" );
            put( "responseSize", "response_size" );
            put( "responseMillis", "response_millis" );
            put( "response500", "response_500" );
            put( "response404", "response_404" );
            put( "response401", "response_401" );
            put( "response403", "response_403" );
        }};
    }

    /**
    * 主键。
    */
    @QueryMeta(expr = "id=?")
    @Schema(title="主键", description = "主键")
    private Long id;

    /**
    * ID数组。
    */
    @QueryMeta(expr = "id in (?)")
    @Schema(title="ID数组", description = "ID数组，可同时匹配多个。")
    private Long[] ids;

    /**
    * 统计时间范围。
    */
    @QueryMeta(expr = "stats_date between ? and ?")
    @Schema(title="统计时间范围", description = "统计时间范围")
    private Date[] statsDateRange;

    /**
    * 建立时间范围。
    */
    @QueryMeta(expr = "create_date between ? and ?")
    @Schema(title="建立时间范围", description = "建立时间范围")
    private Date[] createDateRange;

    /**
    * 用户数。
    */
    @QueryMeta(expr = "user_num=?")
    @Schema(title="用户数", description = "用户数")
    private Long userNum;

    /**
    * 用户数范围。
    */
    @QueryMeta(expr = "user_num between ? and ?")
    @Schema(title="用户数范围", description = "用户数范围")
    private Long[] userNumRange;
	
    /**
    * ip数。
    */
    @QueryMeta(expr = "ip_num=?")
    @Schema(title="ip数", description = "ip数")
    private Long ipNum;

    /**
    * ip数范围。
    */
    @QueryMeta(expr = "ip_num between ? and ?")
    @Schema(title="ip数范围", description = "ip数范围")
    private Long[] ipNumRange;
	
    /**
    * 会话数。
    */
    @QueryMeta(expr = "session_num=?")
    @Schema(title="会话数", description = "会话数")
    private Long sessionNum;

    /**
    * 会话数范围。
    */
    @QueryMeta(expr = "session_num between ? and ?")
    @Schema(title="会话数范围", description = "会话数范围")
    private Long[] sessionNumRange;
	
    /**
    * 服务数。
    */
    @QueryMeta(expr = "service_num=?")
    @Schema(title="服务数", description = "服务数")
    private Long serviceNum;

    /**
    * 服务数范围。
    */
    @QueryMeta(expr = "service_num between ? and ?")
    @Schema(title="服务数范围", description = "服务数范围")
    private Long[] serviceNumRange;
	
    /**
    * api数。
    */
    @QueryMeta(expr = "api_num=?")
    @Schema(title="api数", description = "api数")
    private Long apiNum;

    /**
    * api数范围。
    */
    @QueryMeta(expr = "api_num between ? and ?")
    @Schema(title="api数范围", description = "api数范围")
    private Long[] apiNumRange;
	
    /**
    * 访问量。
    */
    @QueryMeta(expr = "request_num=?")
    @Schema(title="访问量", description = "访问量")
    private Long requestNum;

    /**
    * 访问量范围。
    */
    @QueryMeta(expr = "request_num between ? and ?")
    @Schema(title="访问量范围", description = "访问量范围")
    private Long[] requestNumRange;
	
    /**
    * 请求数据量。
    */
    @QueryMeta(expr = "request_size=?")
    @Schema(title="请求数据量", description = "请求数据量")
    private Long requestSize;

    /**
    * 请求数据量范围。
    */
    @QueryMeta(expr = "request_size between ? and ?")
    @Schema(title="请求数据量范围", description = "请求数据量范围")
    private Long[] requestSizeRange;
	
    /**
    * 响应数据量。
    */
    @QueryMeta(expr = "response_size=?")
    @Schema(title="响应数据量", description = "响应数据量")
    private Long responseSize;

    /**
    * 响应数据量范围。
    */
    @QueryMeta(expr = "response_size between ? and ?")
    @Schema(title="响应数据量范围", description = "响应数据量范围")
    private Long[] responseSizeRange;
	
    /**
    * 响应总时间。
    */
    @QueryMeta(expr = "response_millis=?")
    @Schema(title="响应总时间", description = "响应总时间")
    private Long responseMillis;

    /**
    * 响应总时间范围。
    */
    @QueryMeta(expr = "response_millis between ? and ?")
    @Schema(title="响应总时间范围", description = "响应总时间范围")
    private Long[] responseMillisRange;
	
    /**
    * 500代码数。
    */
    @QueryMeta(expr = "response_500=?")
    @Schema(title="500代码数", description = "500代码数")
    private Long response500;

    /**
    * 500代码数范围。
    */
    @QueryMeta(expr = "response_500 between ? and ?")
    @Schema(title="500代码数范围", description = "500代码数范围")
    private Long[] response500Range;
	
    /**
    * 404代码数。
    */
    @QueryMeta(expr = "response_404=?")
    @Schema(title="404代码数", description = "404代码数")
    private Long response404;

    /**
    * 404代码数范围。
    */
    @QueryMeta(expr = "response_404 between ? and ?")
    @Schema(title="404代码数范围", description = "404代码数范围")
    private Long[] response404Range;
	
    /**
    * 401代码数。
    */
    @QueryMeta(expr = "response_401=?")
    @Schema(title="401代码数", description = "401代码数")
    private Long response401;

    /**
    * 401代码数范围。
    */
    @QueryMeta(expr = "response_401 between ? and ?")
    @Schema(title="401代码数范围", description = "401代码数范围")
    private Long[] response401Range;
	
    /**
    * 403代码数。
    */
    @QueryMeta(expr = "response_403=?")
    @Schema(title="403代码数", description = "403代码数")
    private Long response403;

    /**
    * 403代码数范围。
    */
    @QueryMeta(expr = "response_403 between ? and ?")
    @Schema(title="403代码数范围", description = "403代码数范围")
    private Long[] response403Range;
	

    /**
    * 获取主键。
    */
    public Long getId() {
        return this.id;
    }

    /**
    * 设置主键。
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 设置主键链式调用。
    */
    public AccessSaasStatsQueryParam id(Long id) {
        setId(id);
        return this;
    }

    /**
    * 获取ID数组。
    */
    public Long[] getIds() {
        return this.ids;
    }

    /**
    * 设置ID数组。
    */
    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    /**
    * 设置ID数组链式调用。
    */
    public AccessSaasStatsQueryParam ids(Long[] ids) {
        setIds(ids);
        return this;
    }

    /**
    * 获取统计时间范围。
    */
    public Date[] getStatsDateRange(){
        return this.statsDateRange;
    }

    /**
    * 设置统计时间范围。
    */
    public void setStatsDateRange(Date[] statsDateRange){
        this.statsDateRange = statsDateRange;
    }
	
    /**
    * 设置统计时间范围链式调用。
    */
    public AccessSaasStatsQueryParam statsDateRange(Date[] statsDateRange) {
        setStatsDateRange(statsDateRange);
        return this;
    }
	
    /**
    * 获取建立时间范围。
    */
    public Date[] getCreateDateRange(){
        return this.createDateRange;
    }

    /**
    * 设置建立时间范围。
    */
    public void setCreateDateRange(Date[] createDateRange){
        this.createDateRange = createDateRange;
    }
	
    /**
    * 设置建立时间范围链式调用。
    */
    public AccessSaasStatsQueryParam createDateRange(Date[] createDateRange) {
        setCreateDateRange(createDateRange);
        return this;
    }
	
    /**
    * 获取用户数。
    */
    public Long getUserNum(){
        return this.userNum;
    }

    /**
    * 设置用户数。
    */
    public void setUserNum(Long userNum){
        this.userNum = userNum;
    }
	
    /**
    * 设置用户数链式调用。
    */
    public AccessSaasStatsQueryParam userNum(Long userNum){
        setUserNum(userNum);
        return this;
    }

    /**
    * 获取用户数范围。
    */
    public Long[] getUserNumRange(){
        return this.userNumRange;
    }

    /**
    * 设置用户数范围。
    */
    public void setUserNumRange(Long[] userNumRange){
        this.userNumRange = userNumRange;
    }
	
    /**
    * 设置用户数范围链式调用。
    */
    public AccessSaasStatsQueryParam userNumRange(Long[] userNumRange){
        setUserNumRange(userNumRange);
        return this;
    }
	
    /**
    * 获取ip数。
    */
    public Long getIpNum(){
        return this.ipNum;
    }

    /**
    * 设置ip数。
    */
    public void setIpNum(Long ipNum){
        this.ipNum = ipNum;
    }
	
    /**
    * 设置ip数链式调用。
    */
    public AccessSaasStatsQueryParam ipNum(Long ipNum){
        setIpNum(ipNum);
        return this;
    }

    /**
    * 获取ip数范围。
    */
    public Long[] getIpNumRange(){
        return this.ipNumRange;
    }

    /**
    * 设置ip数范围。
    */
    public void setIpNumRange(Long[] ipNumRange){
        this.ipNumRange = ipNumRange;
    }
	
    /**
    * 设置ip数范围链式调用。
    */
    public AccessSaasStatsQueryParam ipNumRange(Long[] ipNumRange){
        setIpNumRange(ipNumRange);
        return this;
    }
	
    /**
    * 获取会话数。
    */
    public Long getSessionNum(){
        return this.sessionNum;
    }

    /**
    * 设置会话数。
    */
    public void setSessionNum(Long sessionNum){
        this.sessionNum = sessionNum;
    }
	
    /**
    * 设置会话数链式调用。
    */
    public AccessSaasStatsQueryParam sessionNum(Long sessionNum){
        setSessionNum(sessionNum);
        return this;
    }

    /**
    * 获取会话数范围。
    */
    public Long[] getSessionNumRange(){
        return this.sessionNumRange;
    }

    /**
    * 设置会话数范围。
    */
    public void setSessionNumRange(Long[] sessionNumRange){
        this.sessionNumRange = sessionNumRange;
    }
	
    /**
    * 设置会话数范围链式调用。
    */
    public AccessSaasStatsQueryParam sessionNumRange(Long[] sessionNumRange){
        setSessionNumRange(sessionNumRange);
        return this;
    }
	
    /**
    * 获取服务数。
    */
    public Long getServiceNum(){
        return this.serviceNum;
    }

    /**
    * 设置服务数。
    */
    public void setServiceNum(Long serviceNum){
        this.serviceNum = serviceNum;
    }
	
    /**
    * 设置服务数链式调用。
    */
    public AccessSaasStatsQueryParam serviceNum(Long serviceNum){
        setServiceNum(serviceNum);
        return this;
    }

    /**
    * 获取服务数范围。
    */
    public Long[] getServiceNumRange(){
        return this.serviceNumRange;
    }

    /**
    * 设置服务数范围。
    */
    public void setServiceNumRange(Long[] serviceNumRange){
        this.serviceNumRange = serviceNumRange;
    }
	
    /**
    * 设置服务数范围链式调用。
    */
    public AccessSaasStatsQueryParam serviceNumRange(Long[] serviceNumRange){
        setServiceNumRange(serviceNumRange);
        return this;
    }
	
    /**
    * 获取api数。
    */
    public Long getApiNum(){
        return this.apiNum;
    }

    /**
    * 设置api数。
    */
    public void setApiNum(Long apiNum){
        this.apiNum = apiNum;
    }
	
    /**
    * 设置api数链式调用。
    */
    public AccessSaasStatsQueryParam apiNum(Long apiNum){
        setApiNum(apiNum);
        return this;
    }

    /**
    * 获取api数范围。
    */
    public Long[] getApiNumRange(){
        return this.apiNumRange;
    }

    /**
    * 设置api数范围。
    */
    public void setApiNumRange(Long[] apiNumRange){
        this.apiNumRange = apiNumRange;
    }
	
    /**
    * 设置api数范围链式调用。
    */
    public AccessSaasStatsQueryParam apiNumRange(Long[] apiNumRange){
        setApiNumRange(apiNumRange);
        return this;
    }
	
    /**
    * 获取访问量。
    */
    public Long getRequestNum(){
        return this.requestNum;
    }

    /**
    * 设置访问量。
    */
    public void setRequestNum(Long requestNum){
        this.requestNum = requestNum;
    }
	
    /**
    * 设置访问量链式调用。
    */
    public AccessSaasStatsQueryParam requestNum(Long requestNum){
        setRequestNum(requestNum);
        return this;
    }

    /**
    * 获取访问量范围。
    */
    public Long[] getRequestNumRange(){
        return this.requestNumRange;
    }

    /**
    * 设置访问量范围。
    */
    public void setRequestNumRange(Long[] requestNumRange){
        this.requestNumRange = requestNumRange;
    }
	
    /**
    * 设置访问量范围链式调用。
    */
    public AccessSaasStatsQueryParam requestNumRange(Long[] requestNumRange){
        setRequestNumRange(requestNumRange);
        return this;
    }
	
    /**
    * 获取请求数据量。
    */
    public Long getRequestSize(){
        return this.requestSize;
    }

    /**
    * 设置请求数据量。
    */
    public void setRequestSize(Long requestSize){
        this.requestSize = requestSize;
    }
	
    /**
    * 设置请求数据量链式调用。
    */
    public AccessSaasStatsQueryParam requestSize(Long requestSize){
        setRequestSize(requestSize);
        return this;
    }

    /**
    * 获取请求数据量范围。
    */
    public Long[] getRequestSizeRange(){
        return this.requestSizeRange;
    }

    /**
    * 设置请求数据量范围。
    */
    public void setRequestSizeRange(Long[] requestSizeRange){
        this.requestSizeRange = requestSizeRange;
    }
	
    /**
    * 设置请求数据量范围链式调用。
    */
    public AccessSaasStatsQueryParam requestSizeRange(Long[] requestSizeRange){
        setRequestSizeRange(requestSizeRange);
        return this;
    }
	
    /**
    * 获取响应数据量。
    */
    public Long getResponseSize(){
        return this.responseSize;
    }

    /**
    * 设置响应数据量。
    */
    public void setResponseSize(Long responseSize){
        this.responseSize = responseSize;
    }
	
    /**
    * 设置响应数据量链式调用。
    */
    public AccessSaasStatsQueryParam responseSize(Long responseSize){
        setResponseSize(responseSize);
        return this;
    }

    /**
    * 获取响应数据量范围。
    */
    public Long[] getResponseSizeRange(){
        return this.responseSizeRange;
    }

    /**
    * 设置响应数据量范围。
    */
    public void setResponseSizeRange(Long[] responseSizeRange){
        this.responseSizeRange = responseSizeRange;
    }
	
    /**
    * 设置响应数据量范围链式调用。
    */
    public AccessSaasStatsQueryParam responseSizeRange(Long[] responseSizeRange){
        setResponseSizeRange(responseSizeRange);
        return this;
    }
	
    /**
    * 获取响应总时间。
    */
    public Long getResponseMillis(){
        return this.responseMillis;
    }

    /**
    * 设置响应总时间。
    */
    public void setResponseMillis(Long responseMillis){
        this.responseMillis = responseMillis;
    }
	
    /**
    * 设置响应总时间链式调用。
    */
    public AccessSaasStatsQueryParam responseMillis(Long responseMillis){
        setResponseMillis(responseMillis);
        return this;
    }

    /**
    * 获取响应总时间范围。
    */
    public Long[] getResponseMillisRange(){
        return this.responseMillisRange;
    }

    /**
    * 设置响应总时间范围。
    */
    public void setResponseMillisRange(Long[] responseMillisRange){
        this.responseMillisRange = responseMillisRange;
    }
	
    /**
    * 设置响应总时间范围链式调用。
    */
    public AccessSaasStatsQueryParam responseMillisRange(Long[] responseMillisRange){
        setResponseMillisRange(responseMillisRange);
        return this;
    }
	
    /**
    * 获取500代码数。
    */
    public Long getResponse500(){
        return this.response500;
    }

    /**
    * 设置500代码数。
    */
    public void setResponse500(Long response500){
        this.response500 = response500;
    }
	
    /**
    * 设置500代码数链式调用。
    */
    public AccessSaasStatsQueryParam response500(Long response500){
        setResponse500(response500);
        return this;
    }

    /**
    * 获取500代码数范围。
    */
    public Long[] getResponse500Range(){
        return this.response500Range;
    }

    /**
    * 设置500代码数范围。
    */
    public void setResponse500Range(Long[] response500Range){
        this.response500Range = response500Range;
    }
	
    /**
    * 设置500代码数范围链式调用。
    */
    public AccessSaasStatsQueryParam response500Range(Long[] response500Range){
        setResponse500Range(response500Range);
        return this;
    }
	
    /**
    * 获取404代码数。
    */
    public Long getResponse404(){
        return this.response404;
    }

    /**
    * 设置404代码数。
    */
    public void setResponse404(Long response404){
        this.response404 = response404;
    }
	
    /**
    * 设置404代码数链式调用。
    */
    public AccessSaasStatsQueryParam response404(Long response404){
        setResponse404(response404);
        return this;
    }

    /**
    * 获取404代码数范围。
    */
    public Long[] getResponse404Range(){
        return this.response404Range;
    }

    /**
    * 设置404代码数范围。
    */
    public void setResponse404Range(Long[] response404Range){
        this.response404Range = response404Range;
    }
	
    /**
    * 设置404代码数范围链式调用。
    */
    public AccessSaasStatsQueryParam response404Range(Long[] response404Range){
        setResponse404Range(response404Range);
        return this;
    }
	
    /**
    * 获取401代码数。
    */
    public Long getResponse401(){
        return this.response401;
    }

    /**
    * 设置401代码数。
    */
    public void setResponse401(Long response401){
        this.response401 = response401;
    }
	
    /**
    * 设置401代码数链式调用。
    */
    public AccessSaasStatsQueryParam response401(Long response401){
        setResponse401(response401);
        return this;
    }

    /**
    * 获取401代码数范围。
    */
    public Long[] getResponse401Range(){
        return this.response401Range;
    }

    /**
    * 设置401代码数范围。
    */
    public void setResponse401Range(Long[] response401Range){
        this.response401Range = response401Range;
    }
	
    /**
    * 设置401代码数范围链式调用。
    */
    public AccessSaasStatsQueryParam response401Range(Long[] response401Range){
        setResponse401Range(response401Range);
        return this;
    }
	
    /**
    * 获取403代码数。
    */
    public Long getResponse403(){
        return this.response403;
    }

    /**
    * 设置403代码数。
    */
    public void setResponse403(Long response403){
        this.response403 = response403;
    }
	
    /**
    * 设置403代码数链式调用。
    */
    public AccessSaasStatsQueryParam response403(Long response403){
        setResponse403(response403);
        return this;
    }

    /**
    * 获取403代码数范围。
    */
    public Long[] getResponse403Range(){
        return this.response403Range;
    }

    /**
    * 设置403代码数范围。
    */
    public void setResponse403Range(Long[] response403Range){
        this.response403Range = response403Range;
    }
	
    /**
    * 设置403代码数范围链式调用。
    */
    public AccessSaasStatsQueryParam response403Range(Long[] response403Range){
        setResponse403Range(response403Range);
        return this;
    }
	

}