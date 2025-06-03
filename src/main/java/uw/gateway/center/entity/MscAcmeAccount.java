package uw.gateway.center.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.util.JsonUtils;
import uw.dao.DataEntity;
import uw.dao.DataUpdateInfo;
import uw.dao.annotation.ColumnMeta;
import uw.dao.annotation.TableMeta;

import java.io.Serializable;


/**
 * MscAcmeAccount实体类
 * acme账号
 *
 * @author axeon
 */
@TableMeta(tableName="msc_acme_account",tableType="table")
@Schema(title = "acme账号", description = "acme账号")
public class MscAcmeAccount implements DataEntity,Serializable{


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
     * 账号名称
     */
    @ColumnMeta(columnName="account_name", dataType="String", dataSize=200, nullable=true)
    @Schema(title = "账号名称", description = "账号名称", maxLength=200, nullable=true )
    private String accountName;

    /**
     * 账号描述
     */
    @ColumnMeta(columnName="account_desc", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "账号描述", description = "账号描述", maxLength=65535, nullable=true )
    private String accountDesc;

    /**
     * 账户证书算法
     */
    @ColumnMeta(columnName="account_cert_alg", dataType="String", dataSize=20, nullable=true)
    @Schema(title = "账户证书算法", description = "账户证书算法", maxLength=20, nullable=true )
    private String accountCertAlg;

    /**
     * 账户证书密钥
     */
    @ColumnMeta(columnName="account_cert_key", dataType="String", dataSize=65535, nullable=true)
    @Schema(title = "账户证书密钥", description = "账户证书密钥", maxLength=65535, nullable=true )
    private String accountCertKey;

    /**
     * EAB KID
     */
    @ColumnMeta(columnName="eab_id", dataType="String", dataSize=200, nullable=true)
    @Schema(title = "EAB KID", description = "EAB KID", maxLength=200, nullable=true )
    private String eabId;

    /**
     * EAB-KEY
     */
    @ColumnMeta(columnName="eab_key", dataType="String", dataSize=200, nullable=true)
    @Schema(title = "EAB-KEY", description = "EAB-KEY", maxLength=200, nullable=true )
    private String eabKey;

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
        return "msc_acme_account";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "acme账号";
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
     * 获取账号名称。
     */
    public String getAccountName(){
        return this.accountName;
    }

    /**
     * 获取账号描述。
     */
    public String getAccountDesc(){
        return this.accountDesc;
    }

    /**
     * 获取账户证书算法。
     */
    public String getAccountCertAlg(){
        return this.accountCertAlg;
    }

    /**
     * 获取账户证书密钥。
     */
    public String getAccountCertKey(){
        return this.accountCertKey;
    }

    /**
     * 获取EAB KID。
     */
    public String getEabId(){
        return this.eabId;
    }

    /**
     * 获取EAB-KEY。
     */
    public String getEabKey(){
        return this.eabKey;
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
    public MscAcmeAccount id(long id){
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
    public MscAcmeAccount saasId(long saasId){
        setSaasId(saasId);
        return this;
    }

    /**
     * 设置账号名称。
     */
    public void setAccountName(String accountName){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "accountName", this.accountName, accountName, !_IS_LOADED );
        this.accountName = accountName;
    }

    /**
     *  设置账号名称链式调用。
     */
    public MscAcmeAccount accountName(String accountName){
        setAccountName(accountName);
        return this;
    }

    /**
     * 设置账号描述。
     */
    public void setAccountDesc(String accountDesc){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "accountDesc", this.accountDesc, accountDesc, !_IS_LOADED );
        this.accountDesc = accountDesc;
    }

    /**
     *  设置账号描述链式调用。
     */
    public MscAcmeAccount accountDesc(String accountDesc){
        setAccountDesc(accountDesc);
        return this;
    }

    /**
     * 设置账户证书算法。
     */
    public void setAccountCertAlg(String accountCertAlg){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "accountCertAlg", this.accountCertAlg, accountCertAlg, !_IS_LOADED );
        this.accountCertAlg = accountCertAlg;
    }

    /**
     *  设置账户证书算法链式调用。
     */
    public MscAcmeAccount accountCertAlg(String accountCertAlg){
        setAccountCertAlg(accountCertAlg);
        return this;
    }

    /**
     * 设置账户证书密钥。
     */
    public void setAccountCertKey(String accountCertKey){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "accountCertKey", this.accountCertKey, accountCertKey, !_IS_LOADED );
        this.accountCertKey = accountCertKey;
    }

    /**
     *  设置账户证书密钥链式调用。
     */
    public MscAcmeAccount accountCertKey(String accountCertKey){
        setAccountCertKey(accountCertKey);
        return this;
    }

    /**
     * 设置EAB KID。
     */
    public void setEabId(String eabId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "eabId", this.eabId, eabId, !_IS_LOADED );
        this.eabId = eabId;
    }

    /**
     *  设置EAB KID链式调用。
     */
    public MscAcmeAccount eabId(String eabId){
        setEabId(eabId);
        return this;
    }

    /**
     * 设置EAB-KEY。
     */
    public void setEabKey(String eabKey){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "eabKey", this.eabKey, eabKey, !_IS_LOADED );
        this.eabKey = eabKey;
    }

    /**
     *  设置EAB-KEY链式调用。
     */
    public MscAcmeAccount eabKey(String eabKey){
        setEabKey(eabKey);
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
    public MscAcmeAccount createDate(java.util.Date createDate){
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
    public MscAcmeAccount modifyDate(java.util.Date modifyDate){
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
    public MscAcmeAccount state(int state){
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