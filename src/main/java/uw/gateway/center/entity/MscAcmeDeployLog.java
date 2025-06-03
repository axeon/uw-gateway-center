package uw.gateway.center.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.util.JsonUtils;
import uw.dao.DataEntity;
import uw.dao.DataUpdateInfo;
import uw.dao.annotation.ColumnMeta;
import uw.dao.annotation.TableMeta;

import java.io.Serializable;


/**
 * MscAcmeDeployLog实体类
 * acme部署日志
 *
 * @author axeon
 */
@TableMeta(tableName="msc_acme_deploy_log",tableType="table")
@Schema(title = "acme部署日志", description = "acme部署日志")
public class MscAcmeDeployLog implements DataEntity,Serializable{


    /**
     * id
     */
    @ColumnMeta(columnName="id", dataType="long", dataSize=19, nullable=false, primaryKey=true)
    @Schema(title = "id", description = "id", maxLength=19, nullable=false )
    private long id;

    /**
     * saasId
     */
    @ColumnMeta(columnName="saas_id", dataType="long", dataSize=19, nullable=false, primaryKey=true)
    @Schema(title = "saasId", description = "saasId", maxLength=19, nullable=false )
    private long saasId;

    /**
     * 域名id
     */
    @ColumnMeta(columnName="domain_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "域名id", description = "域名id", maxLength=19, nullable=true )
    private long domainId;

    /**
     * 证书id
     */
    @ColumnMeta(columnName="cert_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "证书id", description = "证书id", maxLength=19, nullable=true )
    private long certId;

    /**
     * 部署id
     */
    @ColumnMeta(columnName="deploy_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "部署id", description = "部署id", maxLength=19, nullable=true )
    private long deployId;

    /**
     * 部署信息
     */
    @ColumnMeta(columnName="deploy_info", dataType="String", dataSize=500, nullable=true)
    @Schema(title = "部署信息", description = "部署信息", maxLength=500, nullable=true )
    private String deployInfo;

    /**
     * 部署日志
     */
    @ColumnMeta(columnName="deploy_log", dataType="String", dataSize=2147483646, nullable=true)
    @Schema(title = "部署日志", description = "部署日志", maxLength=2147483646, nullable=true )
    private String deployLog;

    /**
     * 部署时间
     */
    @ColumnMeta(columnName="deploy_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "部署时间", description = "部署时间", maxLength=23, nullable=true )
    private java.util.Date deployDate;

    /**
     * 状态值: -1: 删除 0: 冻结 1: 正常
     */
    @ColumnMeta(columnName="state", dataType="int", dataSize=10, nullable=false)
    @Schema(title = "状态值: -1: 删除 0: 冻结 1: 正常", description = "状态值: -1: 删除 0: 冻结 1: 正常", maxLength=10, nullable=false )
    private int state;

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
        return "msc_acme_deploy_log";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "acme部署日志";
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
     * 获取id。
     */
    public long getId(){
        return this.id;
    }

    /**
     * 获取saasId。
     */
    public long getSaasId(){
        return this.saasId;
    }

    /**
     * 获取域名id。
     */
    public long getDomainId(){
        return this.domainId;
    }

    /**
     * 获取证书id。
     */
    public long getCertId(){
        return this.certId;
    }

    /**
     * 获取部署id。
     */
    public long getDeployId(){
        return this.deployId;
    }

    /**
     * 获取部署信息。
     */
    public String getDeployInfo(){
        return this.deployInfo;
    }

    /**
     * 获取部署日志。
     */
    public String getDeployLog(){
        return this.deployLog;
    }

    /**
     * 获取部署时间。
     */
    public java.util.Date getDeployDate(){
        return this.deployDate;
    }

    /**
     * 获取状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public int getState(){
        return this.state;
    }


    /**
     * 设置id。
     */
    public void setId(long id){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "id", this.id, id, !_IS_LOADED );
        this.id = id;
    }

    /**
     *  设置id链式调用。
     */
    public MscAcmeDeployLog id(long id){
        setId(id);
        return this;
    }

    /**
     * 设置saasId。
     */
    public void setSaasId(long saasId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "saasId", this.saasId, saasId, !_IS_LOADED );
        this.saasId = saasId;
    }

    /**
     *  设置saasId链式调用。
     */
    public MscAcmeDeployLog saasId(long saasId){
        setSaasId(saasId);
        return this;
    }

    /**
     * 设置域名id。
     */
    public void setDomainId(long domainId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "domainId", this.domainId, domainId, !_IS_LOADED );
        this.domainId = domainId;
    }

    /**
     *  设置域名id链式调用。
     */
    public MscAcmeDeployLog domainId(long domainId){
        setDomainId(domainId);
        return this;
    }

    /**
     * 设置证书id。
     */
    public void setCertId(long certId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "certId", this.certId, certId, !_IS_LOADED );
        this.certId = certId;
    }

    /**
     *  设置证书id链式调用。
     */
    public MscAcmeDeployLog certId(long certId){
        setCertId(certId);
        return this;
    }

    /**
     * 设置部署id。
     */
    public void setDeployId(long deployId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployId", this.deployId, deployId, !_IS_LOADED );
        this.deployId = deployId;
    }

    /**
     *  设置部署id链式调用。
     */
    public MscAcmeDeployLog deployId(long deployId){
        setDeployId(deployId);
        return this;
    }

    /**
     * 设置部署信息。
     */
    public void setDeployInfo(String deployInfo){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployInfo", this.deployInfo, deployInfo, !_IS_LOADED );
        this.deployInfo = deployInfo;
    }

    /**
     *  设置部署信息链式调用。
     */
    public MscAcmeDeployLog deployInfo(String deployInfo){
        setDeployInfo(deployInfo);
        return this;
    }

    /**
     * 设置部署日志。
     */
    public void setDeployLog(String deployLog){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployLog", this.deployLog, deployLog, !_IS_LOADED );
        this.deployLog = deployLog;
    }

    /**
     *  设置部署日志链式调用。
     */
    public MscAcmeDeployLog deployLog(String deployLog){
        setDeployLog(deployLog);
        return this;
    }

    /**
     * 设置部署时间。
     */
    public void setDeployDate(java.util.Date deployDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployDate", this.deployDate, deployDate, !_IS_LOADED );
        this.deployDate = deployDate;
    }

    /**
     *  设置部署时间链式调用。
     */
    public MscAcmeDeployLog deployDate(java.util.Date deployDate){
        setDeployDate(deployDate);
        return this;
    }

    /**
     * 设置状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public void setState(int state){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "state", this.state, state, !_IS_LOADED );
        this.state = state;
    }

    /**
     *  设置状态值: -1: 删除 0: 冻结 1: 正常链式调用。
     */
    public MscAcmeDeployLog state(int state){
        setState(state);
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