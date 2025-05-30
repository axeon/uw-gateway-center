package uw.gateway.center.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.util.JsonUtils;
import uw.dao.DataEntity;
import uw.dao.DataUpdateInfo;
import uw.dao.annotation.ColumnMeta;
import uw.dao.annotation.TableMeta;

import java.io.Serializable;


/**
 * AccessGlobalStats实体类
 * 全局访问统计
 *
 * @author axeon
 */
@TableMeta(tableName="access_global_stats",tableType="table")
@Schema(title = "全局访问统计", description = "全局访问统计")
public class AccessGlobalStats implements DataEntity,Serializable{


    /**
     * 主键
     */
    @ColumnMeta(columnName="id", dataType="long", dataSize=19, nullable=false, primaryKey=true)
    @Schema(title = "主键", description = "主键", maxLength=19, nullable=false )
    private long id;

    /**
     * 统计时间
     */
    @ColumnMeta(columnName="stats_date", dataType="java.util.Date", dataSize=19, nullable=true)
    @Schema(title = "统计时间", description = "统计时间", maxLength=19, nullable=true )
    private java.util.Date statsDate;

    /**
     * 建立时间
     */
    @ColumnMeta(columnName="create_date", dataType="java.util.Date", dataSize=19, nullable=true)
    @Schema(title = "建立时间", description = "建立时间", maxLength=19, nullable=true )
    private java.util.Date createDate;

    /**
     * 系统数
     */
    @ColumnMeta(columnName="saas_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "系统数", description = "系统数", maxLength=19, nullable=true )
    private long saasNum;

    /**
     * 用户数
     */
    @ColumnMeta(columnName="user_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "用户数", description = "用户数", maxLength=19, nullable=true )
    private long userNum;

    /**
     * ip数
     */
    @ColumnMeta(columnName="ip_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "ip数", description = "ip数", maxLength=19, nullable=true )
    private long ipNum;

    /**
     * 会话数
     */
    @ColumnMeta(columnName="session_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "会话数", description = "会话数", maxLength=19, nullable=true )
    private long sessionNum;

    /**
     * 服务数
     */
    @ColumnMeta(columnName="service_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "服务数", description = "服务数", maxLength=19, nullable=true )
    private long serviceNum;

    /**
     * api数
     */
    @ColumnMeta(columnName="api_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "api数", description = "api数", maxLength=19, nullable=true )
    private long apiNum;

    /**
     * 访问量
     */
    @ColumnMeta(columnName="request_num", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "访问量", description = "访问量", maxLength=19, nullable=true )
    private long requestNum;

    /**
     * 请求数据量
     */
    @ColumnMeta(columnName="request_size", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "请求数据量", description = "请求数据量", maxLength=19, nullable=true )
    private long requestSize;

    /**
     * 响应数据量
     */
    @ColumnMeta(columnName="response_size", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "响应数据量", description = "响应数据量", maxLength=19, nullable=true )
    private long responseSize;

    /**
     * 响应总时间
     */
    @ColumnMeta(columnName="response_millis", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "响应总时间", description = "响应总时间", maxLength=19, nullable=true )
    private long responseMillis;

    /**
     * 500代码数
     */
    @ColumnMeta(columnName="response_500", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "500代码数", description = "500代码数", maxLength=19, nullable=true )
    private long response500;

    /**
     * 404代码数
     */
    @ColumnMeta(columnName="response_404", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "404代码数", description = "404代码数", maxLength=19, nullable=true )
    private long response404;

    /**
     * 401代码数
     */
    @ColumnMeta(columnName="response_401", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "401代码数", description = "401代码数", maxLength=19, nullable=true )
    private long response401;

    /**
     * 403代码数
     */
    @ColumnMeta(columnName="response_403", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "403代码数", description = "403代码数", maxLength=19, nullable=true )
    private long response403;

    /**
     * 数据更新信息.
     */
    private transient DataUpdateInfo _UPDATED_INFO = null;

    /**
     * 是否加载完成.
     */
    private transient boolean _IS_LOADED;

    /**
     * 获得实体的表名。
     */
    @Override
    public String ENTITY_TABLE(){
        return "access_global_stats";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "全局访问统计";
    }

    /**
     * 获得主键
     */
    @Override
    public Serializable ENTITY_ID(){
        return getId();
    }

    /**
     * 获取更新信息.
     */
    @Override
    public DataUpdateInfo GET_UPDATED_INFO() {
        return this._UPDATED_INFO;
    }

    /**
     * 清除更新信息.
     */
    @Override
    public void CLEAR_UPDATED_INFO() {
        _UPDATED_INFO = null;
    }


    /**
     * 获取主键。
     */
    public long getId(){
        return this.id;
    }

    /**
     * 获取统计时间。
     */
    public java.util.Date getStatsDate(){
        return this.statsDate;
    }

    /**
     * 获取建立时间。
     */
    public java.util.Date getCreateDate(){
        return this.createDate;
    }

    /**
     * 获取系统数。
     */
    public long getSaasNum(){
        return this.saasNum;
    }

    /**
     * 获取用户数。
     */
    public long getUserNum(){
        return this.userNum;
    }

    /**
     * 获取ip数。
     */
    public long getIpNum(){
        return this.ipNum;
    }

    /**
     * 获取会话数。
     */
    public long getSessionNum(){
        return this.sessionNum;
    }

    /**
     * 获取服务数。
     */
    public long getServiceNum(){
        return this.serviceNum;
    }

    /**
     * 获取api数。
     */
    public long getApiNum(){
        return this.apiNum;
    }

    /**
     * 获取访问量。
     */
    public long getRequestNum(){
        return this.requestNum;
    }

    /**
     * 获取请求数据量。
     */
    public long getRequestSize(){
        return this.requestSize;
    }

    /**
     * 获取响应数据量。
     */
    public long getResponseSize(){
        return this.responseSize;
    }

    /**
     * 获取响应总时间。
     */
    public long getResponseMillis(){
        return this.responseMillis;
    }

    /**
     * 获取500代码数。
     */
    public long getResponse500(){
        return this.response500;
    }

    /**
     * 获取404代码数。
     */
    public long getResponse404(){
        return this.response404;
    }

    /**
     * 获取401代码数。
     */
    public long getResponse401(){
        return this.response401;
    }

    /**
     * 获取403代码数。
     */
    public long getResponse403(){
        return this.response403;
    }


    /**
     * 设置主键。
     */
    public void setId(long id){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "id", this.id, id, !_IS_LOADED );
        this.id = id;
    }

    /**
     *  设置主键链式调用。
     */
    public AccessGlobalStats id(long id){
        setId(id);
        return this;
    }

    /**
     * 设置统计时间。
     */
    public void setStatsDate(java.util.Date statsDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "statsDate", this.statsDate, statsDate, !_IS_LOADED );
        this.statsDate = statsDate;
    }

    /**
     *  设置统计时间链式调用。
     */
    public AccessGlobalStats statsDate(java.util.Date statsDate){
        setStatsDate(statsDate);
        return this;
    }

    /**
     * 设置建立时间。
     */
    public void setCreateDate(java.util.Date createDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "createDate", this.createDate, createDate, !_IS_LOADED );
        this.createDate = createDate;
    }

    /**
     *  设置建立时间链式调用。
     */
    public AccessGlobalStats createDate(java.util.Date createDate){
        setCreateDate(createDate);
        return this;
    }

    /**
     * 设置系统数。
     */
    public void setSaasNum(long saasNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "saasNum", this.saasNum, saasNum, !_IS_LOADED );
        this.saasNum = saasNum;
    }

    /**
     *  设置系统数链式调用。
     */
    public AccessGlobalStats saasNum(long saasNum){
        setSaasNum(saasNum);
        return this;
    }

    /**
     * 设置用户数。
     */
    public void setUserNum(long userNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "userNum", this.userNum, userNum, !_IS_LOADED );
        this.userNum = userNum;
    }

    /**
     *  设置用户数链式调用。
     */
    public AccessGlobalStats userNum(long userNum){
        setUserNum(userNum);
        return this;
    }

    /**
     * 设置ip数。
     */
    public void setIpNum(long ipNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "ipNum", this.ipNum, ipNum, !_IS_LOADED );
        this.ipNum = ipNum;
    }

    /**
     *  设置ip数链式调用。
     */
    public AccessGlobalStats ipNum(long ipNum){
        setIpNum(ipNum);
        return this;
    }

    /**
     * 设置会话数。
     */
    public void setSessionNum(long sessionNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "sessionNum", this.sessionNum, sessionNum, !_IS_LOADED );
        this.sessionNum = sessionNum;
    }

    /**
     *  设置会话数链式调用。
     */
    public AccessGlobalStats sessionNum(long sessionNum){
        setSessionNum(sessionNum);
        return this;
    }

    /**
     * 设置服务数。
     */
    public void setServiceNum(long serviceNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "serviceNum", this.serviceNum, serviceNum, !_IS_LOADED );
        this.serviceNum = serviceNum;
    }

    /**
     *  设置服务数链式调用。
     */
    public AccessGlobalStats serviceNum(long serviceNum){
        setServiceNum(serviceNum);
        return this;
    }

    /**
     * 设置api数。
     */
    public void setApiNum(long apiNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "apiNum", this.apiNum, apiNum, !_IS_LOADED );
        this.apiNum = apiNum;
    }

    /**
     *  设置api数链式调用。
     */
    public AccessGlobalStats apiNum(long apiNum){
        setApiNum(apiNum);
        return this;
    }

    /**
     * 设置访问量。
     */
    public void setRequestNum(long requestNum){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "requestNum", this.requestNum, requestNum, !_IS_LOADED );
        this.requestNum = requestNum;
    }

    /**
     *  设置访问量链式调用。
     */
    public AccessGlobalStats requestNum(long requestNum){
        setRequestNum(requestNum);
        return this;
    }

    /**
     * 设置请求数据量。
     */
    public void setRequestSize(long requestSize){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "requestSize", this.requestSize, requestSize, !_IS_LOADED );
        this.requestSize = requestSize;
    }

    /**
     *  设置请求数据量链式调用。
     */
    public AccessGlobalStats requestSize(long requestSize){
        setRequestSize(requestSize);
        return this;
    }

    /**
     * 设置响应数据量。
     */
    public void setResponseSize(long responseSize){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "responseSize", this.responseSize, responseSize, !_IS_LOADED );
        this.responseSize = responseSize;
    }

    /**
     *  设置响应数据量链式调用。
     */
    public AccessGlobalStats responseSize(long responseSize){
        setResponseSize(responseSize);
        return this;
    }

    /**
     * 设置响应总时间。
     */
    public void setResponseMillis(long responseMillis){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "responseMillis", this.responseMillis, responseMillis, !_IS_LOADED );
        this.responseMillis = responseMillis;
    }

    /**
     *  设置响应总时间链式调用。
     */
    public AccessGlobalStats responseMillis(long responseMillis){
        setResponseMillis(responseMillis);
        return this;
    }

    /**
     * 设置500代码数。
     */
    public void setResponse500(long response500){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "response500", this.response500, response500, !_IS_LOADED );
        this.response500 = response500;
    }

    /**
     *  设置500代码数链式调用。
     */
    public AccessGlobalStats response500(long response500){
        setResponse500(response500);
        return this;
    }

    /**
     * 设置404代码数。
     */
    public void setResponse404(long response404){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "response404", this.response404, response404, !_IS_LOADED );
        this.response404 = response404;
    }

    /**
     *  设置404代码数链式调用。
     */
    public AccessGlobalStats response404(long response404){
        setResponse404(response404);
        return this;
    }

    /**
     * 设置401代码数。
     */
    public void setResponse401(long response401){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "response401", this.response401, response401, !_IS_LOADED );
        this.response401 = response401;
    }

    /**
     *  设置401代码数链式调用。
     */
    public AccessGlobalStats response401(long response401){
        setResponse401(response401);
        return this;
    }

    /**
     * 设置403代码数。
     */
    public void setResponse403(long response403){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "response403", this.response403, response403, !_IS_LOADED );
        this.response403 = response403;
    }

    /**
     *  设置403代码数链式调用。
     */
    public AccessGlobalStats response403(long response403){
        setResponse403(response403);
        return this;
    }

    /**
     * 重载toString方法.
     */
    @Override
    public String toString() {
        return JsonUtils.toString(this);
    }

}