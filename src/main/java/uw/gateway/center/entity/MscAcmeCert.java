package uw.gateway.center.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.util.JsonUtils;
import uw.dao.DataEntity;
import uw.dao.DataUpdateInfo;
import uw.dao.annotation.ColumnMeta;
import uw.dao.annotation.TableMeta;

import java.io.Serializable;


/**
 * MscAcmeCert实体类
 * acme证书
 *
 * @author axeon
 */
@TableMeta(tableName="msc_acme_cert",tableType="table")
@Schema(title = "acme证书", description = "acme证书")
public class MscAcmeCert implements DataEntity,Serializable{


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
     * 域名id
     */
    @ColumnMeta(columnName="domain_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "域名id", description = "域名id", maxLength=19, nullable=true )
    private long domainId;

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
     * 证书算法
     */
    @ColumnMeta(columnName="cert_alg", dataType="String", dataSize=20, nullable=true)
    @Schema(title = "证书算法", description = "证书算法", maxLength=20, nullable=true )
    private String certAlg;

    /**
     * 证书密钥
     */
    @ColumnMeta(columnName="cert_key", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "证书密钥", description = "证书密钥", maxLength=65535, nullable=true )
    private String certKey;

    /**
     * 证书数据
     */
    @ColumnMeta(columnName="cert_data", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "证书数据", description = "证书数据", maxLength=65535, nullable=true )
    private String certData;

    /**
     * 证书日志
     */
    @ColumnMeta(columnName="cert_log", dataType="String", dataSize=2147483646, nullable=true)
    @Schema(title = "证书日志", description = "证书日志", maxLength=2147483646, nullable=true )
    private String certLog;

    /**
     * 激活日期
     */
    @ColumnMeta(columnName="active_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "激活日期", description = "激活日期", maxLength=23, nullable=true )
    private java.util.Date activeDate;

    /**
     * 失效时间
     */
    @ColumnMeta(columnName="expire_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "失效时间", description = "失效时间", maxLength=23, nullable=true )
    private java.util.Date expireDate;

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
        return "msc_acme_cert";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "acme证书";
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
     * 获取域名id。
     */
    public long getDomainId(){
        return this.domainId;
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
     * 获取证书算法。
     */
    public String getCertAlg(){
        return this.certAlg;
    }

    /**
     * 获取证书密钥。
     */
    public String getCertKey(){
        return this.certKey;
    }

    /**
     * 获取证书数据。
     */
    public String getCertData(){
        return this.certData;
    }

    /**
     * 获取证书日志。
     */
    public String getCertLog(){
        return this.certLog;
    }

    /**
     * 获取激活日期。
     */
    public java.util.Date getActiveDate(){
        return this.activeDate;
    }

    /**
     * 获取失效时间。
     */
    public java.util.Date getExpireDate(){
        return this.expireDate;
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
    public MscAcmeCert id(long id){
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
    public MscAcmeCert saasId(long saasId){
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
    public MscAcmeCert accountId(long accountId){
        setAccountId(accountId);
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
    public MscAcmeCert domainId(long domainId){
        setDomainId(domainId);
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
    public MscAcmeCert domainName(String domainName){
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
    public MscAcmeCert domainAlias(String domainAlias){
        setDomainAlias(domainAlias);
        return this;
    }

    /**
     * 设置证书算法。
     */
    public void setCertAlg(String certAlg){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "certAlg", this.certAlg, certAlg, !_IS_LOADED );
        this.certAlg = certAlg;
    }

    /**
     *  设置证书算法链式调用。
     */
    public MscAcmeCert certAlg(String certAlg){
        setCertAlg(certAlg);
        return this;
    }

    /**
     * 设置证书密钥。
     */
    public void setCertKey(String certKey){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "certKey", this.certKey, certKey, !_IS_LOADED );
        this.certKey = certKey;
    }

    /**
     *  设置证书密钥链式调用。
     */
    public MscAcmeCert certKey(String certKey){
        setCertKey(certKey);
        return this;
    }

    /**
     * 设置证书数据。
     */
    public void setCertData(String certData){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "certData", this.certData, certData, !_IS_LOADED );
        this.certData = certData;
    }

    /**
     *  设置证书数据链式调用。
     */
    public MscAcmeCert certData(String certData){
        setCertData(certData);
        return this;
    }

    /**
     * 设置证书日志。
     */
    public void setCertLog(String certLog){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "certLog", this.certLog, certLog, !_IS_LOADED );
        this.certLog = certLog;
    }

    /**
     *  设置证书日志链式调用。
     */
    public MscAcmeCert certLog(String certLog){
        setCertLog(certLog);
        return this;
    }

    /**
     * 设置激活日期。
     */
    public void setActiveDate(java.util.Date activeDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "activeDate", this.activeDate, activeDate, !_IS_LOADED );
        this.activeDate = activeDate;
    }

    /**
     *  设置激活日期链式调用。
     */
    public MscAcmeCert activeDate(java.util.Date activeDate){
        setActiveDate(activeDate);
        return this;
    }

    /**
     * 设置失效时间。
     */
    public void setExpireDate(java.util.Date expireDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "expireDate", this.expireDate, expireDate, !_IS_LOADED );
        this.expireDate = expireDate;
    }

    /**
     *  设置失效时间链式调用。
     */
    public MscAcmeCert expireDate(java.util.Date expireDate){
        setExpireDate(expireDate);
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
    public MscAcmeCert createDate(java.util.Date createDate){
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
    public MscAcmeCert modifyDate(java.util.Date modifyDate){
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
    public MscAcmeCert state(int state){
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