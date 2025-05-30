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
 * MscAcmeDeploy实体类
 * acme部署
 *
 * @author axeon
 */
@TableMeta(tableName="msc_acme_deploy",tableType="table")
@Schema(title = "acme部署", description = "acme部署")
public class MscAcmeDeploy implements DataEntity,Serializable{


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
     * 部署名称
     */
    @ColumnMeta(columnName="deploy_name", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "部署名称", description = "部署名称", maxLength=100, nullable=true )
    private String deployName;

    /**
     * 部署描述
     */
    @ColumnMeta(columnName="deploy_desc", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "部署描述", description = "部署描述", maxLength=65535, nullable=true )
    private String deployDesc;

    /**
     * 部署目标
     */
    @ColumnMeta(columnName="deploy_vendor", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "部署目标", description = "部署目标", maxLength=100, nullable=true )
    private String deployVendor;

    /**
     * 部署参数
     */
    @ColumnMeta(columnName="deploy_param", dataType="String", dataSize=1073741824, nullable=true)
    @Schema(title = "部署参数", description = "部署参数", maxLength=1073741824, nullable=true )
    @JsonRawValue(value = false)
    private String deployParam;

    /**
     * 上次更新时间
     */
    @ColumnMeta(columnName="last_update", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "上次更新时间", description = "上次更新时间", maxLength=23, nullable=true )
    private java.util.Date lastUpdate;

    /**
     * 上次更新时间
     */
    @ColumnMeta(columnName="last_active_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "上次更新时间", description = "上次更新时间", maxLength=23, nullable=true )
    private java.util.Date lastActiveDate;

    /**
     * 最近证书过期时间
     */
    @ColumnMeta(columnName="last_expire_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "最近证书过期时间", description = "最近证书过期时间", maxLength=23, nullable=true )
    private java.util.Date lastExpireDate;

    /**
     * 创建时间
     */
    @ColumnMeta(columnName="create_date", dataType="java.util.Date", dataSize=23, nullable=false)
    @Schema(title = "创建时间", description = "创建时间", maxLength=23, nullable=false )
    private java.util.Date createDate;

    /**
     * 修改时间
     */
    @ColumnMeta(columnName="modify_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "修改时间", description = "修改时间", maxLength=23, nullable=true )
    private java.util.Date modifyDate;

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
        return "msc_acme_deploy";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "acme部署";
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
     * 获取部署名称。
     */
    public String getDeployName(){
        return this.deployName;
    }

    /**
     * 获取部署描述。
     */
    public String getDeployDesc(){
        return this.deployDesc;
    }

    /**
     * 获取部署目标。
     */
    public String getDeployVendor(){
        return this.deployVendor;
    }

    /**
     * 获取部署参数。
     */
    public String getDeployParam(){
        return this.deployParam;
    }

    /**
     * 获取上次更新时间。
     */
    public java.util.Date getLastUpdate(){
        return this.lastUpdate;
    }

    /**
     * 获取上次更新时间。
     */
    public java.util.Date getLastActiveDate(){
        return this.lastActiveDate;
    }

    /**
     * 获取最近证书过期时间。
     */
    public java.util.Date getLastExpireDate(){
        return this.lastExpireDate;
    }

    /**
     * 获取创建时间。
     */
    public java.util.Date getCreateDate(){
        return this.createDate;
    }

    /**
     * 获取修改时间。
     */
    public java.util.Date getModifyDate(){
        return this.modifyDate;
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
    public MscAcmeDeploy id(long id){
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
    public MscAcmeDeploy saasId(long saasId){
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
    public MscAcmeDeploy domainId(long domainId){
        setDomainId(domainId);
        return this;
    }

    /**
     * 设置部署名称。
     */
    public void setDeployName(String deployName){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployName", this.deployName, deployName, !_IS_LOADED );
        this.deployName = deployName;
    }

    /**
     *  设置部署名称链式调用。
     */
    public MscAcmeDeploy deployName(String deployName){
        setDeployName(deployName);
        return this;
    }

    /**
     * 设置部署描述。
     */
    public void setDeployDesc(String deployDesc){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployDesc", this.deployDesc, deployDesc, !_IS_LOADED );
        this.deployDesc = deployDesc;
    }

    /**
     *  设置部署描述链式调用。
     */
    public MscAcmeDeploy deployDesc(String deployDesc){
        setDeployDesc(deployDesc);
        return this;
    }

    /**
     * 设置部署目标。
     */
    public void setDeployVendor(String deployVendor){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployVendor", this.deployVendor, deployVendor, !_IS_LOADED );
        this.deployVendor = deployVendor;
    }

    /**
     *  设置部署目标链式调用。
     */
    public MscAcmeDeploy deployVendor(String deployVendor){
        setDeployVendor(deployVendor);
        return this;
    }

    /**
     * 设置部署参数。
     */
    public void setDeployParam(String deployParam){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "deployParam", this.deployParam, deployParam, !_IS_LOADED );
        this.deployParam = deployParam;
    }

    /**
     *  设置部署参数链式调用。
     */
    public MscAcmeDeploy deployParam(String deployParam){
        setDeployParam(deployParam);
        return this;
    }

    /**
     * 设置上次更新时间。
     */
    public void setLastUpdate(java.util.Date lastUpdate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "lastUpdate", this.lastUpdate, lastUpdate, !_IS_LOADED );
        this.lastUpdate = lastUpdate;
    }

    /**
     *  设置上次更新时间链式调用。
     */
    public MscAcmeDeploy lastUpdate(java.util.Date lastUpdate){
        setLastUpdate(lastUpdate);
        return this;
    }

    /**
     * 设置上次更新时间。
     */
    public void setLastActiveDate(java.util.Date lastActiveDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "lastActiveDate", this.lastActiveDate, lastActiveDate, !_IS_LOADED );
        this.lastActiveDate = lastActiveDate;
    }

    /**
     *  设置上次更新时间链式调用。
     */
    public MscAcmeDeploy lastActiveDate(java.util.Date lastActiveDate){
        setLastActiveDate(lastActiveDate);
        return this;
    }

    /**
     * 设置最近证书过期时间。
     */
    public void setLastExpireDate(java.util.Date lastExpireDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "lastExpireDate", this.lastExpireDate, lastExpireDate, !_IS_LOADED );
        this.lastExpireDate = lastExpireDate;
    }

    /**
     *  设置最近证书过期时间链式调用。
     */
    public MscAcmeDeploy lastExpireDate(java.util.Date lastExpireDate){
        setLastExpireDate(lastExpireDate);
        return this;
    }

    /**
     * 设置创建时间。
     */
    public void setCreateDate(java.util.Date createDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "createDate", this.createDate, createDate, !_IS_LOADED );
        this.createDate = createDate;
    }

    /**
     *  设置创建时间链式调用。
     */
    public MscAcmeDeploy createDate(java.util.Date createDate){
        setCreateDate(createDate);
        return this;
    }

    /**
     * 设置修改时间。
     */
    public void setModifyDate(java.util.Date modifyDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "modifyDate", this.modifyDate, modifyDate, !_IS_LOADED );
        this.modifyDate = modifyDate;
    }

    /**
     *  设置修改时间链式调用。
     */
    public MscAcmeDeploy modifyDate(java.util.Date modifyDate){
        setModifyDate(modifyDate);
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
    public MscAcmeDeploy state(int state){
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