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
 * MscAcmeDomain实体类
 * acme域名
 *
 * @author axeon
 */
@TableMeta(tableName="msc_acme_domain",tableType="table")
@Schema(title = "acme域名", description = "acme域名")
public class MscAcmeDomain implements DataEntity,Serializable{


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
     * 账户ID
     */
    @ColumnMeta(columnName="account_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "账户ID", description = "账户ID", maxLength=19, nullable=true )
    private long accountId;

    /**
     * 域名名称
     */
    @ColumnMeta(columnName="domain_name", dataType="String", dataSize=200, nullable=true)
    @Schema(title = "域名名称", description = "域名名称", maxLength=200, nullable=true )
    private String domainName;

    /**
     * 别名列表
     */
    @ColumnMeta(columnName="domain_alias", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "别名列表", description = "别名列表", maxLength=65535, nullable=true )
    private String domainAlias;

    /**
     * 域名描述
     */
    @ColumnMeta(columnName="domain_desc", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "域名描述", description = "域名描述", maxLength=65535, nullable=true )
    private String domainDesc;

    /**
     * 域名证书算法
     */
    @ColumnMeta(columnName="domain_cert_alg", dataType="String", dataSize=20, nullable=true)
    @Schema(title = "域名证书算法", description = "域名证书算法", maxLength=20, nullable=true )
    private String domainCertAlg;

    /**
     * 域名证书密钥
     */
    @ColumnMeta(columnName="domain_cert_key", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "域名证书密钥", description = "域名证书密钥", maxLength=65535, nullable=true )
    private String domainCertKey;

    /**
     * acme服务商
     */
    @ColumnMeta(columnName="acme_vendor", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "acme服务商", description = "acme服务商", maxLength=100, nullable=true )
    private String acmeVendor;

    /**
     * dns供应商
     */
    @ColumnMeta(columnName="dns_vendor", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "dns供应商", description = "dns供应商", maxLength=100, nullable=true )
    private String dnsVendor;

    /**
     * dns参数
     */
    @ColumnMeta(columnName="dns_param", dataType="String", dataSize=1073741824, nullable=true)
    @Schema(title = "dns参数", description = "dns参数", maxLength=1073741824, nullable=true )
    @JsonRawValue(value = false)
    private String dnsParam;

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
        return "msc_acme_domain";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "acme域名";
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
     * 获取账户ID。
     */
    public long getAccountId(){
        return this.accountId;
    }

    /**
     * 获取域名名称。
     */
    public String getDomainName(){
        return this.domainName;
    }

    /**
     * 获取别名列表。
     */
    public String getDomainAlias(){
        return this.domainAlias;
    }

    /**
     * 获取域名描述。
     */
    public String getDomainDesc(){
        return this.domainDesc;
    }

    /**
     * 获取域名证书算法。
     */
    public String getDomainCertAlg(){
        return this.domainCertAlg;
    }

    /**
     * 获取域名证书密钥。
     */
    public String getDomainCertKey(){
        return this.domainCertKey;
    }

    /**
     * 获取acme服务商。
     */
    public String getAcmeVendor(){
        return this.acmeVendor;
    }

    /**
     * 获取dns供应商。
     */
    public String getDnsVendor(){
        return this.dnsVendor;
    }

    /**
     * 获取dns参数。
     */
    public String getDnsParam(){
        return this.dnsParam;
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
    public MscAcmeDomain id(long id){
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
    public MscAcmeDomain saasId(long saasId){
        setSaasId(saasId);
        return this;
    }

    /**
     * 设置账户ID。
     */
    public void setAccountId(long accountId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "accountId", this.accountId, accountId, !_IS_LOADED );
        this.accountId = accountId;
    }

    /**
     *  设置账户ID链式调用。
     */
    public MscAcmeDomain accountId(long accountId){
        setAccountId(accountId);
        return this;
    }

    /**
     * 设置域名名称。
     */
    public void setDomainName(String domainName){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "domainName", this.domainName, domainName, !_IS_LOADED );
        this.domainName = domainName;
    }

    /**
     *  设置域名名称链式调用。
     */
    public MscAcmeDomain domainName(String domainName){
        setDomainName(domainName);
        return this;
    }

    /**
     * 设置别名列表。
     */
    public void setDomainAlias(String domainAlias){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "domainAlias", this.domainAlias, domainAlias, !_IS_LOADED );
        this.domainAlias = domainAlias;
    }

    /**
     *  设置别名列表链式调用。
     */
    public MscAcmeDomain domainAlias(String domainAlias){
        setDomainAlias(domainAlias);
        return this;
    }

    /**
     * 设置域名描述。
     */
    public void setDomainDesc(String domainDesc){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "domainDesc", this.domainDesc, domainDesc, !_IS_LOADED );
        this.domainDesc = domainDesc;
    }

    /**
     *  设置域名描述链式调用。
     */
    public MscAcmeDomain domainDesc(String domainDesc){
        setDomainDesc(domainDesc);
        return this;
    }

    /**
     * 设置域名证书算法。
     */
    public void setDomainCertAlg(String domainCertAlg){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "domainCertAlg", this.domainCertAlg, domainCertAlg, !_IS_LOADED );
        this.domainCertAlg = domainCertAlg;
    }

    /**
     *  设置域名证书算法链式调用。
     */
    public MscAcmeDomain domainCertAlg(String domainCertAlg){
        setDomainCertAlg(domainCertAlg);
        return this;
    }

    /**
     * 设置域名证书密钥。
     */
    public void setDomainCertKey(String domainCertKey){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "domainCertKey", this.domainCertKey, domainCertKey, !_IS_LOADED );
        this.domainCertKey = domainCertKey;
    }

    /**
     *  设置域名证书密钥链式调用。
     */
    public MscAcmeDomain domainCertKey(String domainCertKey){
        setDomainCertKey(domainCertKey);
        return this;
    }

    /**
     * 设置acme服务商。
     */
    public void setAcmeVendor(String acmeVendor){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "acmeVendor", this.acmeVendor, acmeVendor, !_IS_LOADED );
        this.acmeVendor = acmeVendor;
    }

    /**
     *  设置acme服务商链式调用。
     */
    public MscAcmeDomain acmeVendor(String acmeVendor){
        setAcmeVendor(acmeVendor);
        return this;
    }

    /**
     * 设置dns供应商。
     */
    public void setDnsVendor(String dnsVendor){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "dnsVendor", this.dnsVendor, dnsVendor, !_IS_LOADED );
        this.dnsVendor = dnsVendor;
    }

    /**
     *  设置dns供应商链式调用。
     */
    public MscAcmeDomain dnsVendor(String dnsVendor){
        setDnsVendor(dnsVendor);
        return this;
    }

    /**
     * 设置dns参数。
     */
    public void setDnsParam(String dnsParam){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "dnsParam", this.dnsParam, dnsParam, !_IS_LOADED );
        this.dnsParam = dnsParam;
    }

    /**
     *  设置dns参数链式调用。
     */
    public MscAcmeDomain dnsParam(String dnsParam){
        setDnsParam(dnsParam);
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
    public MscAcmeDomain lastUpdate(java.util.Date lastUpdate){
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
    public MscAcmeDomain lastActiveDate(java.util.Date lastActiveDate){
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
    public MscAcmeDomain lastExpireDate(java.util.Date lastExpireDate){
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
    public MscAcmeDomain createDate(java.util.Date createDate){
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
    public MscAcmeDomain modifyDate(java.util.Date modifyDate){
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
    public MscAcmeDomain state(int state){
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