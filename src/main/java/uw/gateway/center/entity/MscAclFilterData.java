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
 * MscAclFilterData实体类
 * IP访问控制数据
 *
 * @author axeon
 */
@TableMeta(tableName="msc_acl_filter_data",tableType="table")
@Schema(title = "IP访问控制数据", description = "IP访问控制数据")
public class MscAclFilterData implements DataEntity,Serializable{


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
     * 过滤器id
     */
    @ColumnMeta(columnName="filter_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "过滤器id", description = "过滤器id", maxLength=19, nullable=true )
    private long filterId;

    /**
     * ip信息
     */
    @ColumnMeta(columnName="ip_info", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "ip信息", description = "ip信息", maxLength=100, nullable=true )
    private String ipInfo;

    /**
     * ip开始
     */
    @ColumnMeta(columnName="ip_start", dataType="String", dataSize=50, nullable=true)
    @Schema(title = "ip开始", description = "ip开始", maxLength=50, nullable=true )
    private String ipStart;

    /**
     * ip结束
     */
    @ColumnMeta(columnName="ip_end", dataType="String", dataSize=50, nullable=true)
    @Schema(title = "ip结束", description = "ip结束", maxLength=50, nullable=true )
    private String ipEnd;

    /**
     * 申请人ID
     */
    @ColumnMeta(columnName="apply_user_id", dataType="long", dataSize=19, nullable=false)
    @Schema(title = "申请人ID", description = "申请人ID", maxLength=19, nullable=false )
    private long applyUserId;

    /**
     * 申请人IP
     */
    @ColumnMeta(columnName="apply_user_ip", dataType="String", dataSize=50, nullable=true)
    @Schema(title = "申请人IP", description = "申请人IP", maxLength=50, nullable=true )
    private String applyUserIp;

    /**
     * 申请人用户名
     */
    @ColumnMeta(columnName="apply_user_info", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "申请人用户名", description = "申请人用户名", maxLength=100, nullable=true )
    private String applyUserInfo;

    /**
     * 申请备注
     */
    @ColumnMeta(columnName="apply_remark", dataType="String", dataSize=500, nullable=true)
    @Schema(title = "申请备注", description = "申请备注", maxLength=500, nullable=true )
    private String applyRemark;

    /**
     * 请求时间
     */
    @ColumnMeta(columnName="apply_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "请求时间", description = "请求时间", maxLength=23, nullable=true )
    private java.util.Date applyDate;

    /**
     * 审批人ID
     */
    @ColumnMeta(columnName="audit_user_id", dataType="long", dataSize=19, nullable=true)
    @Schema(title = "审批人ID", description = "审批人ID", maxLength=19, nullable=true )
    private long auditUserId;

    /**
     * 审批用户IP
     */
    @ColumnMeta(columnName="audit_user_ip", dataType="String", dataSize=50, nullable=true)
    @Schema(title = "审批用户IP", description = "审批用户IP", maxLength=50, nullable=true )
    private String auditUserIp;

    /**
     * 审批人用户名
     */
    @ColumnMeta(columnName="audit_user_info", dataType="String", dataSize=100, nullable=true)
    @Schema(title = "审批人用户名", description = "审批人用户名", maxLength=100, nullable=true )
    private String auditUserInfo;

    /**
     * 审批备注
     */
    @ColumnMeta(columnName="audit_remark", dataType="String", dataSize=500, nullable=true)
    @Schema(title = "审批备注", description = "审批备注", maxLength=500, nullable=true )
    private String auditRemark;

    /**
     * 审批时间
     */
    @ColumnMeta(columnName="audit_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "审批时间", description = "审批时间", maxLength=23, nullable=true )
    private java.util.Date auditDate;

    /**
     * 审批状态
     */
    @ColumnMeta(columnName="audit_state", dataType="int", dataSize=10, nullable=false)
    @Schema(title = "审批状态", description = "审批状态", maxLength=10, nullable=false )
    private int auditState;

    /**
     * 过期时间
     */
    @ColumnMeta(columnName="expire_date", dataType="java.util.Date", dataSize=23, nullable=true)
    @Schema(title = "过期时间", description = "过期时间", maxLength=23, nullable=true )
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
        return "msc_acl_filter_data";
    }

    /**
     * 获得实体的表注释。
     */
    @Override
    public String ENTITY_NAME(){
        return "IP访问控制数据";
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
     * 获取过滤器id。
     */
    public long getFilterId(){
        return this.filterId;
    }

    /**
     * 获取ip信息。
     */
    public String getIpInfo(){
        return this.ipInfo;
    }

    /**
     * 获取ip开始。
     */
    public String getIpStart(){
        return this.ipStart;
    }

    /**
     * 获取ip结束。
     */
    public String getIpEnd(){
        return this.ipEnd;
    }

    /**
     * 获取申请人ID。
     */
    public long getApplyUserId(){
        return this.applyUserId;
    }

    /**
     * 获取申请人IP。
     */
    public String getApplyUserIp(){
        return this.applyUserIp;
    }

    /**
     * 获取申请人用户名。
     */
    public String getApplyUserInfo(){
        return this.applyUserInfo;
    }

    /**
     * 获取申请备注。
     */
    public String getApplyRemark(){
        return this.applyRemark;
    }

    /**
     * 获取请求时间。
     */
    public java.util.Date getApplyDate(){
        return this.applyDate;
    }

    /**
     * 获取审批人ID。
     */
    public long getAuditUserId(){
        return this.auditUserId;
    }

    /**
     * 获取审批用户IP。
     */
    public String getAuditUserIp(){
        return this.auditUserIp;
    }

    /**
     * 获取审批人用户名。
     */
    public String getAuditUserInfo(){
        return this.auditUserInfo;
    }

    /**
     * 获取审批备注。
     */
    public String getAuditRemark(){
        return this.auditRemark;
    }

    /**
     * 获取审批时间。
     */
    public java.util.Date getAuditDate(){
        return this.auditDate;
    }

    /**
     * 获取审批状态。
     */
    public int getAuditState(){
        return this.auditState;
    }

    /**
     * 获取过期时间。
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
    public MscAclFilterData id(long id){
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
    public MscAclFilterData saasId(long saasId){
        setSaasId(saasId);
        return this;
    }

    /**
     * 设置过滤器id。
     */
    public void setFilterId(long filterId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "filterId", this.filterId, filterId, !_IS_LOADED );
        this.filterId = filterId;
    }

    /**
     *  设置过滤器id链式调用。
     */
    public MscAclFilterData filterId(long filterId){
        setFilterId(filterId);
        return this;
    }

    /**
     * 设置ip信息。
     */
    public void setIpInfo(String ipInfo){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "ipInfo", this.ipInfo, ipInfo, !_IS_LOADED );
        this.ipInfo = ipInfo;
    }

    /**
     *  设置ip信息链式调用。
     */
    public MscAclFilterData ipInfo(String ipInfo){
        setIpInfo(ipInfo);
        return this;
    }

    /**
     * 设置ip开始。
     */
    public void setIpStart(String ipStart){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "ipStart", this.ipStart, ipStart, !_IS_LOADED );
        this.ipStart = ipStart;
    }

    /**
     *  设置ip开始链式调用。
     */
    public MscAclFilterData ipStart(String ipStart){
        setIpStart(ipStart);
        return this;
    }

    /**
     * 设置ip结束。
     */
    public void setIpEnd(String ipEnd){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "ipEnd", this.ipEnd, ipEnd, !_IS_LOADED );
        this.ipEnd = ipEnd;
    }

    /**
     *  设置ip结束链式调用。
     */
    public MscAclFilterData ipEnd(String ipEnd){
        setIpEnd(ipEnd);
        return this;
    }

    /**
     * 设置申请人ID。
     */
    public void setApplyUserId(long applyUserId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "applyUserId", this.applyUserId, applyUserId, !_IS_LOADED );
        this.applyUserId = applyUserId;
    }

    /**
     *  设置申请人ID链式调用。
     */
    public MscAclFilterData applyUserId(long applyUserId){
        setApplyUserId(applyUserId);
        return this;
    }

    /**
     * 设置申请人IP。
     */
    public void setApplyUserIp(String applyUserIp){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "applyUserIp", this.applyUserIp, applyUserIp, !_IS_LOADED );
        this.applyUserIp = applyUserIp;
    }

    /**
     *  设置申请人IP链式调用。
     */
    public MscAclFilterData applyUserIp(String applyUserIp){
        setApplyUserIp(applyUserIp);
        return this;
    }

    /**
     * 设置申请人用户名。
     */
    public void setApplyUserInfo(String applyUserInfo){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "applyUserInfo", this.applyUserInfo, applyUserInfo, !_IS_LOADED );
        this.applyUserInfo = applyUserInfo;
    }

    /**
     *  设置申请人用户名链式调用。
     */
    public MscAclFilterData applyUserInfo(String applyUserInfo){
        setApplyUserInfo(applyUserInfo);
        return this;
    }

    /**
     * 设置申请备注。
     */
    public void setApplyRemark(String applyRemark){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "applyRemark", this.applyRemark, applyRemark, !_IS_LOADED );
        this.applyRemark = applyRemark;
    }

    /**
     *  设置申请备注链式调用。
     */
    public MscAclFilterData applyRemark(String applyRemark){
        setApplyRemark(applyRemark);
        return this;
    }

    /**
     * 设置请求时间。
     */
    public void setApplyDate(java.util.Date applyDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "applyDate", this.applyDate, applyDate, !_IS_LOADED );
        this.applyDate = applyDate;
    }

    /**
     *  设置请求时间链式调用。
     */
    public MscAclFilterData applyDate(java.util.Date applyDate){
        setApplyDate(applyDate);
        return this;
    }

    /**
     * 设置审批人ID。
     */
    public void setAuditUserId(long auditUserId){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "auditUserId", this.auditUserId, auditUserId, !_IS_LOADED );
        this.auditUserId = auditUserId;
    }

    /**
     *  设置审批人ID链式调用。
     */
    public MscAclFilterData auditUserId(long auditUserId){
        setAuditUserId(auditUserId);
        return this;
    }

    /**
     * 设置审批用户IP。
     */
    public void setAuditUserIp(String auditUserIp){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "auditUserIp", this.auditUserIp, auditUserIp, !_IS_LOADED );
        this.auditUserIp = auditUserIp;
    }

    /**
     *  设置审批用户IP链式调用。
     */
    public MscAclFilterData auditUserIp(String auditUserIp){
        setAuditUserIp(auditUserIp);
        return this;
    }

    /**
     * 设置审批人用户名。
     */
    public void setAuditUserInfo(String auditUserInfo){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "auditUserInfo", this.auditUserInfo, auditUserInfo, !_IS_LOADED );
        this.auditUserInfo = auditUserInfo;
    }

    /**
     *  设置审批人用户名链式调用。
     */
    public MscAclFilterData auditUserInfo(String auditUserInfo){
        setAuditUserInfo(auditUserInfo);
        return this;
    }

    /**
     * 设置审批备注。
     */
    public void setAuditRemark(String auditRemark){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "auditRemark", this.auditRemark, auditRemark, !_IS_LOADED );
        this.auditRemark = auditRemark;
    }

    /**
     *  设置审批备注链式调用。
     */
    public MscAclFilterData auditRemark(String auditRemark){
        setAuditRemark(auditRemark);
        return this;
    }

    /**
     * 设置审批时间。
     */
    public void setAuditDate(java.util.Date auditDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "auditDate", this.auditDate, auditDate, !_IS_LOADED );
        this.auditDate = auditDate;
    }

    /**
     *  设置审批时间链式调用。
     */
    public MscAclFilterData auditDate(java.util.Date auditDate){
        setAuditDate(auditDate);
        return this;
    }

    /**
     * 设置审批状态。
     */
    public void setAuditState(int auditState){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "auditState", this.auditState, auditState, !_IS_LOADED );
        this.auditState = auditState;
    }

    /**
     *  设置审批状态链式调用。
     */
    public MscAclFilterData auditState(int auditState){
        setAuditState(auditState);
        return this;
    }

    /**
     * 设置过期时间。
     */
    public void setExpireDate(java.util.Date expireDate){
        _UPDATED_INFO = DataUpdateInfo.addUpdateInfo(_UPDATED_INFO, "expireDate", this.expireDate, expireDate, !_IS_LOADED );
        this.expireDate = expireDate;
    }

    /**
     *  设置过期时间链式调用。
     */
    public MscAclFilterData expireDate(java.util.Date expireDate){
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
    public MscAclFilterData createDate(java.util.Date createDate){
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
    public MscAclFilterData modifyDate(java.util.Date modifyDate){
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
    public MscAclFilterData state(int state){
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