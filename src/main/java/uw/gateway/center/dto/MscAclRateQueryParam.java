package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* 系统流控配置列表查询参数。
*/
@Schema(title = "系统流控配置列表查询参数", description = "系统流控配置列表查询参数")
public class MscAclRateQueryParam extends AuthPageQueryParam{

    public MscAclRateQueryParam() {
        super();
    }

    public MscAclRateQueryParam(Long saasId) {
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
            put( "saasLevel", "saas_level" );
            put( "userId", "user_id" );
            put( "userType", "user_type" );
            put( "limitName", "limit_name" );
            put( "limitDesc", "limit_desc" );
            put( "limitType", "limit_type" );
            put( "limitUri", "limit_uri" );
            put( "limitSeconds", "limit_seconds" );
            put( "limitRequests", "limit_requests" );
            put( "limitBytes", "limit_bytes" );
            put( "applyUserId", "apply_user_id" );
            put( "applyUserIp", "apply_user_ip" );
            put( "applyUserInfo", "apply_user_info" );
            put( "applyRemark", "apply_remark" );
            put( "applyDate", "apply_date" );
            put( "auditUserId", "audit_user_id" );
            put( "auditUserIp", "audit_user_ip" );
            put( "auditUserInfo", "audit_user_info" );
            put( "auditRemark", "audit_remark" );
            put( "auditDate", "audit_date" );
            put( "auditState", "audit_state" );
            put( "expireDate", "expire_date" );
            put( "createDate", "create_date" );
            put( "modifyDate", "modify_date" );
            put( "state", "state" );
        }};
    }

    /**
    * id。
    */
    @QueryMeta(expr = "id=?")
    @Schema(title="id", description = "id")
    private Long id;

    /**
    * ID数组。
    */
    @QueryMeta(expr = "id in (?)")
    @Schema(title="ID数组", description = "ID数组，可同时匹配多个。")
    private Long[] ids;

    /**
    * SAAS级别。
    */
    @QueryMeta(expr = "saas_level=?")
    @Schema(title="SAAS级别", description = "SAAS级别")
    private Integer saasLevel;

    /**
    * SAAS级别范围。
    */
    @QueryMeta(expr = "saas_level between ? and ?")
    @Schema(title="SAAS级别范围", description = "SAAS级别范围")
    private Integer[] saasLevelRange;
	
    /**
    * 用户ID。
    */
    @QueryMeta(expr = "user_id=?")
    @Schema(title="用户ID", description = "用户ID")
    private Long userId;
	
    /**
    * 用户类型。
    */
    @QueryMeta(expr = "user_type=?")
    @Schema(title="用户类型", description = "用户类型")
    private Integer userType;
	
    /**
    * 限速名称。
    */
    @QueryMeta(expr = "limit_name like ?")
    @Schema(title="限速名称", description = "限速名称")
    private String limitName;
	
    /**
    * 限速描述。
    */
    @QueryMeta(expr = "limit_desc like ?")
    @Schema(title="限速描述", description = "限速描述")
    private String limitDesc;
	
    /**
    * 限速类型。
    */
    @QueryMeta(expr = "limit_type=?")
    @Schema(title="限速类型", description = "限速类型")
    private Integer limitType;
	
    /**
    * 限速资源。
    */
    @QueryMeta(expr = "limit_uri like ?")
    @Schema(title="限速资源", description = "限速资源")
    private String limitUri;
	
    /**
    * 限速秒数。
    */
    @QueryMeta(expr = "limit_seconds=?")
    @Schema(title="限速秒数", description = "限速秒数")
    private Integer limitSeconds;

    /**
    * 限速秒数范围。
    */
    @QueryMeta(expr = "limit_seconds between ? and ?")
    @Schema(title="限速秒数范围", description = "限速秒数范围")
    private Integer[] limitSecondsRange;
	
    /**
    * 限速请求。
    */
    @QueryMeta(expr = "limit_requests=?")
    @Schema(title="限速请求", description = "限速请求")
    private Integer limitRequests;

    /**
    * 限速请求范围。
    */
    @QueryMeta(expr = "limit_requests between ? and ?")
    @Schema(title="限速请求范围", description = "限速请求范围")
    private Integer[] limitRequestsRange;
	
    /**
    * 限速字节数。
    */
    @QueryMeta(expr = "limit_bytes=?")
    @Schema(title="限速字节数", description = "限速字节数")
    private Integer limitBytes;

    /**
    * 限速字节数范围。
    */
    @QueryMeta(expr = "limit_bytes between ? and ?")
    @Schema(title="限速字节数范围", description = "限速字节数范围")
    private Integer[] limitBytesRange;
	
    /**
    * 申请人ID。
    */
    @QueryMeta(expr = "apply_user_id=?")
    @Schema(title="申请人ID", description = "申请人ID")
    private Long applyUserId;
	
    /**
    * 申请人IP。
    */
    @QueryMeta(expr = "apply_user_ip like ?")
    @Schema(title="申请人IP", description = "申请人IP")
    private String applyUserIp;
	
    /**
    * 申请人用户名。
    */
    @QueryMeta(expr = "apply_user_info like ?")
    @Schema(title="申请人用户名", description = "申请人用户名")
    private String applyUserInfo;
	
    /**
    * 申请备注。
    */
    @QueryMeta(expr = "apply_remark like ?")
    @Schema(title="申请备注", description = "申请备注")
    private String applyRemark;
	
    /**
    * 请求时间范围。
    */
    @QueryMeta(expr = "apply_date between ? and ?")
    @Schema(title="请求时间范围", description = "请求时间范围")
    private Date[] applyDateRange;

    /**
    * 审批人ID。
    */
    @QueryMeta(expr = "audit_user_id=?")
    @Schema(title="审批人ID", description = "审批人ID")
    private Long auditUserId;
	
    /**
    * 审批用户IP。
    */
    @QueryMeta(expr = "audit_user_ip like ?")
    @Schema(title="审批用户IP", description = "审批用户IP")
    private String auditUserIp;
	
    /**
    * 审批人用户名。
    */
    @QueryMeta(expr = "audit_user_info like ?")
    @Schema(title="审批人用户名", description = "审批人用户名")
    private String auditUserInfo;
	
    /**
    * 审批备注。
    */
    @QueryMeta(expr = "audit_remark like ?")
    @Schema(title="审批备注", description = "审批备注")
    private String auditRemark;
	
    /**
    * 审批时间范围。
    */
    @QueryMeta(expr = "audit_date between ? and ?")
    @Schema(title="审批时间范围", description = "审批时间范围")
    private Date[] auditDateRange;

    /**
    * 审批状态。
    */
    @QueryMeta(expr = "audit_state=?")
    @Schema(title="审批状态", description = "审批状态")
    private Integer auditState;
	
    /**
    * 过期时间范围。
    */
    @QueryMeta(expr = "expire_date between ? and ?")
    @Schema(title="过期时间范围", description = "过期时间范围")
    private Date[] expireDateRange;

    /**
    * 创建时间范围。
    */
    @QueryMeta(expr = "create_date between ? and ?")
    @Schema(title="创建时间范围", description = "创建时间范围")
    private Date[] createDateRange;

    /**
    * 修改时间范围。
    */
    @QueryMeta(expr = "modify_date between ? and ?")
    @Schema(title="修改时间范围", description = "修改时间范围")
    private Date[] modifyDateRange;

    /**
    * 状态值: -1: 删除 0: 冻结 1: 正常。
    */
    @QueryMeta(expr = "state=?")
    @Schema(title="状态值: -1: 删除 0: 冻结 1: 正常", description = "状态值: -1: 删除 0: 冻结 1: 正常")
    private Integer state;

    /**
    * 状态值: -1: 删除 0: 冻结 1: 正常数组。
    */
    @QueryMeta(expr = "state in (?)")
    @Schema(title="状态值: -1: 删除 0: 冻结 1: 正常数组", description = "状态值: -1: 删除 0: 冻结 1: 正常数组，可同时匹配多个状态。")
    private Integer[] states;

    /**
    * 大于等于状态值: -1: 删除 0: 冻结 1: 正常。
    */
    @QueryMeta(expr = "state>=?")
    @Schema(title="大于等于状态值: -1: 删除 0: 冻结 1: 正常", description = "大于等于状态值: -1: 删除 0: 冻结 1: 正常")
    private Integer stateGte;

    /**
    * 小于等于状态值: -1: 删除 0: 冻结 1: 正常。
    */
    @QueryMeta(expr = "state<=?")
    @Schema(title="小于等于状态值: -1: 删除 0: 冻结 1: 正常", description = "小于等于状态值: -1: 删除 0: 冻结 1: 正常")
    private Integer stateLte;


    /**
    * 获取id。
    */
    public Long getId() {
        return this.id;
    }

    /**
    * 设置id。
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 设置id链式调用。
    */
    public MscAclRateQueryParam id(Long id) {
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
    public MscAclRateQueryParam ids(Long[] ids) {
        setIds(ids);
        return this;
    }

    /**
    * 获取SAAS级别。
    */
    public Integer getSaasLevel(){
        return this.saasLevel;
    }

    /**
    * 设置SAAS级别。
    */
    public void setSaasLevel(Integer saasLevel){
        this.saasLevel = saasLevel;
    }
	
    /**
    * 设置SAAS级别链式调用。
    */
    public MscAclRateQueryParam saasLevel(Integer saasLevel){
        setSaasLevel(saasLevel);
        return this;
    }

    /**
    * 获取SAAS级别范围。
    */
    public Integer[] getSaasLevelRange(){
        return this.saasLevelRange;
    }

    /**
    * 设置SAAS级别范围。
    */
    public void setSaasLevelRange(Integer[] saasLevelRange){
        this.saasLevelRange = saasLevelRange;
    }
	
    /**
    * 设置SAAS级别范围链式调用。
    */
    public MscAclRateQueryParam saasLevelRange(Integer[] saasLevelRange){
        setSaasLevelRange(saasLevelRange);
        return this;
    }
	
    /**
    * 获取用户ID。
    */
    public Long getUserId(){
        return this.userId;
    }

    /**
    * 设置用户ID。
    */
    public void setUserId(Long userId){
        this.userId = userId;
    }
	
    /**
    * 设置用户ID链式调用。
    */
	public MscAclRateQueryParam userId(Long userId){
        setUserId(userId);
        return this;
    }
	
    /**
    * 获取用户类型。
    */
    public Integer getUserType(){
        return this.userType;
    }

    /**
    * 设置用户类型。
    */
    public void setUserType(Integer userType){
        this.userType = userType;
    }
	
    /**
    * 设置用户类型链式调用。
    */
	public MscAclRateQueryParam userType(Integer userType){
        setUserType(userType);
        return this;
    }
	
    /**
    * 获取限速名称。
    */
    public String getLimitName(){
        return this.limitName;
    }

    /**
    * 设置限速名称。
    */
    public void setLimitName(String limitName){
        this.limitName = limitName;
    }
	
    /**
    * 设置限速名称链式调用。
    */
    public MscAclRateQueryParam limitName(String limitName) {
        setLimitName(limitName);
        return this;
    }
	
    /**
    * 获取限速描述。
    */
    public String getLimitDesc(){
        return this.limitDesc;
    }

    /**
    * 设置限速描述。
    */
    public void setLimitDesc(String limitDesc){
        this.limitDesc = limitDesc;
    }
	
    /**
    * 设置限速描述链式调用。
    */
    public MscAclRateQueryParam limitDesc(String limitDesc) {
        setLimitDesc(limitDesc);
        return this;
    }
	
    /**
    * 获取限速类型。
    */
    public Integer getLimitType(){
        return this.limitType;
    }

    /**
    * 设置限速类型。
    */
    public void setLimitType(Integer limitType){
        this.limitType = limitType;
    }
	
    /**
    * 设置限速类型链式调用。
    */
	public MscAclRateQueryParam limitType(Integer limitType){
        setLimitType(limitType);
        return this;
    }
	
    /**
    * 获取限速资源。
    */
    public String getLimitUri(){
        return this.limitUri;
    }

    /**
    * 设置限速资源。
    */
    public void setLimitUri(String limitUri){
        this.limitUri = limitUri;
    }
	
    /**
    * 设置限速资源链式调用。
    */
    public MscAclRateQueryParam limitUri(String limitUri) {
        setLimitUri(limitUri);
        return this;
    }
	
    /**
    * 获取限速秒数。
    */
    public Integer getLimitSeconds(){
        return this.limitSeconds;
    }

    /**
    * 设置限速秒数。
    */
    public void setLimitSeconds(Integer limitSeconds){
        this.limitSeconds = limitSeconds;
    }
	
    /**
    * 设置限速秒数链式调用。
    */
    public MscAclRateQueryParam limitSeconds(Integer limitSeconds){
        setLimitSeconds(limitSeconds);
        return this;
    }

    /**
    * 获取限速秒数范围。
    */
    public Integer[] getLimitSecondsRange(){
        return this.limitSecondsRange;
    }

    /**
    * 设置限速秒数范围。
    */
    public void setLimitSecondsRange(Integer[] limitSecondsRange){
        this.limitSecondsRange = limitSecondsRange;
    }
	
    /**
    * 设置限速秒数范围链式调用。
    */
    public MscAclRateQueryParam limitSecondsRange(Integer[] limitSecondsRange){
        setLimitSecondsRange(limitSecondsRange);
        return this;
    }
	
    /**
    * 获取限速请求。
    */
    public Integer getLimitRequests(){
        return this.limitRequests;
    }

    /**
    * 设置限速请求。
    */
    public void setLimitRequests(Integer limitRequests){
        this.limitRequests = limitRequests;
    }
	
    /**
    * 设置限速请求链式调用。
    */
    public MscAclRateQueryParam limitRequests(Integer limitRequests){
        setLimitRequests(limitRequests);
        return this;
    }

    /**
    * 获取限速请求范围。
    */
    public Integer[] getLimitRequestsRange(){
        return this.limitRequestsRange;
    }

    /**
    * 设置限速请求范围。
    */
    public void setLimitRequestsRange(Integer[] limitRequestsRange){
        this.limitRequestsRange = limitRequestsRange;
    }
	
    /**
    * 设置限速请求范围链式调用。
    */
    public MscAclRateQueryParam limitRequestsRange(Integer[] limitRequestsRange){
        setLimitRequestsRange(limitRequestsRange);
        return this;
    }
	
    /**
    * 获取限速字节数。
    */
    public Integer getLimitBytes(){
        return this.limitBytes;
    }

    /**
    * 设置限速字节数。
    */
    public void setLimitBytes(Integer limitBytes){
        this.limitBytes = limitBytes;
    }
	
    /**
    * 设置限速字节数链式调用。
    */
    public MscAclRateQueryParam limitBytes(Integer limitBytes){
        setLimitBytes(limitBytes);
        return this;
    }

    /**
    * 获取限速字节数范围。
    */
    public Integer[] getLimitBytesRange(){
        return this.limitBytesRange;
    }

    /**
    * 设置限速字节数范围。
    */
    public void setLimitBytesRange(Integer[] limitBytesRange){
        this.limitBytesRange = limitBytesRange;
    }
	
    /**
    * 设置限速字节数范围链式调用。
    */
    public MscAclRateQueryParam limitBytesRange(Integer[] limitBytesRange){
        setLimitBytesRange(limitBytesRange);
        return this;
    }
	
    /**
    * 获取申请人ID。
    */
    public Long getApplyUserId(){
        return this.applyUserId;
    }

    /**
    * 设置申请人ID。
    */
    public void setApplyUserId(Long applyUserId){
        this.applyUserId = applyUserId;
    }
	
    /**
    * 设置申请人ID链式调用。
    */
	public MscAclRateQueryParam applyUserId(Long applyUserId){
        setApplyUserId(applyUserId);
        return this;
    }
	
    /**
    * 获取申请人IP。
    */
    public String getApplyUserIp(){
        return this.applyUserIp;
    }

    /**
    * 设置申请人IP。
    */
    public void setApplyUserIp(String applyUserIp){
        this.applyUserIp = applyUserIp;
    }
	
    /**
    * 设置申请人IP链式调用。
    */
    public MscAclRateQueryParam applyUserIp(String applyUserIp) {
        setApplyUserIp(applyUserIp);
        return this;
    }
	
    /**
    * 获取申请人用户名。
    */
    public String getApplyUserInfo(){
        return this.applyUserInfo;
    }

    /**
    * 设置申请人用户名。
    */
    public void setApplyUserInfo(String applyUserInfo){
        this.applyUserInfo = applyUserInfo;
    }
	
    /**
    * 设置申请人用户名链式调用。
    */
    public MscAclRateQueryParam applyUserInfo(String applyUserInfo) {
        setApplyUserInfo(applyUserInfo);
        return this;
    }
	
    /**
    * 获取申请备注。
    */
    public String getApplyRemark(){
        return this.applyRemark;
    }

    /**
    * 设置申请备注。
    */
    public void setApplyRemark(String applyRemark){
        this.applyRemark = applyRemark;
    }
	
    /**
    * 设置申请备注链式调用。
    */
    public MscAclRateQueryParam applyRemark(String applyRemark) {
        setApplyRemark(applyRemark);
        return this;
    }
	
    /**
    * 获取请求时间范围。
    */
    public Date[] getApplyDateRange(){
        return this.applyDateRange;
    }

    /**
    * 设置请求时间范围。
    */
    public void setApplyDateRange(Date[] applyDateRange){
        this.applyDateRange = applyDateRange;
    }
	
    /**
    * 设置请求时间范围链式调用。
    */
    public MscAclRateQueryParam applyDateRange(Date[] applyDateRange) {
        setApplyDateRange(applyDateRange);
        return this;
    }
	
    /**
    * 获取审批人ID。
    */
    public Long getAuditUserId(){
        return this.auditUserId;
    }

    /**
    * 设置审批人ID。
    */
    public void setAuditUserId(Long auditUserId){
        this.auditUserId = auditUserId;
    }
	
    /**
    * 设置审批人ID链式调用。
    */
	public MscAclRateQueryParam auditUserId(Long auditUserId){
        setAuditUserId(auditUserId);
        return this;
    }
	
    /**
    * 获取审批用户IP。
    */
    public String getAuditUserIp(){
        return this.auditUserIp;
    }

    /**
    * 设置审批用户IP。
    */
    public void setAuditUserIp(String auditUserIp){
        this.auditUserIp = auditUserIp;
    }
	
    /**
    * 设置审批用户IP链式调用。
    */
    public MscAclRateQueryParam auditUserIp(String auditUserIp) {
        setAuditUserIp(auditUserIp);
        return this;
    }
	
    /**
    * 获取审批人用户名。
    */
    public String getAuditUserInfo(){
        return this.auditUserInfo;
    }

    /**
    * 设置审批人用户名。
    */
    public void setAuditUserInfo(String auditUserInfo){
        this.auditUserInfo = auditUserInfo;
    }
	
    /**
    * 设置审批人用户名链式调用。
    */
    public MscAclRateQueryParam auditUserInfo(String auditUserInfo) {
        setAuditUserInfo(auditUserInfo);
        return this;
    }
	
    /**
    * 获取审批备注。
    */
    public String getAuditRemark(){
        return this.auditRemark;
    }

    /**
    * 设置审批备注。
    */
    public void setAuditRemark(String auditRemark){
        this.auditRemark = auditRemark;
    }
	
    /**
    * 设置审批备注链式调用。
    */
    public MscAclRateQueryParam auditRemark(String auditRemark) {
        setAuditRemark(auditRemark);
        return this;
    }
	
    /**
    * 获取审批时间范围。
    */
    public Date[] getAuditDateRange(){
        return this.auditDateRange;
    }

    /**
    * 设置审批时间范围。
    */
    public void setAuditDateRange(Date[] auditDateRange){
        this.auditDateRange = auditDateRange;
    }
	
    /**
    * 设置审批时间范围链式调用。
    */
    public MscAclRateQueryParam auditDateRange(Date[] auditDateRange) {
        setAuditDateRange(auditDateRange);
        return this;
    }
	
    /**
    * 获取审批状态。
    */
    public Integer getAuditState(){
        return this.auditState;
    }

    /**
    * 设置审批状态。
    */
    public void setAuditState(Integer auditState){
        this.auditState = auditState;
    }
	
    /**
    * 设置审批状态链式调用。
    */
	public MscAclRateQueryParam auditState(Integer auditState){
        setAuditState(auditState);
        return this;
    }
	
    /**
    * 获取过期时间范围。
    */
    public Date[] getExpireDateRange(){
        return this.expireDateRange;
    }

    /**
    * 设置过期时间范围。
    */
    public void setExpireDateRange(Date[] expireDateRange){
        this.expireDateRange = expireDateRange;
    }
	
    /**
    * 设置过期时间范围链式调用。
    */
    public MscAclRateQueryParam expireDateRange(Date[] expireDateRange) {
        setExpireDateRange(expireDateRange);
        return this;
    }
	
    /**
    * 获取创建时间范围。
    */
    public Date[] getCreateDateRange(){
        return this.createDateRange;
    }

    /**
    * 设置创建时间范围。
    */
    public void setCreateDateRange(Date[] createDateRange){
        this.createDateRange = createDateRange;
    }
	
    /**
    * 设置创建时间范围链式调用。
    */
    public MscAclRateQueryParam createDateRange(Date[] createDateRange) {
        setCreateDateRange(createDateRange);
        return this;
    }
	
    /**
    * 获取修改时间范围。
    */
    public Date[] getModifyDateRange(){
        return this.modifyDateRange;
    }

    /**
    * 设置修改时间范围。
    */
    public void setModifyDateRange(Date[] modifyDateRange){
        this.modifyDateRange = modifyDateRange;
    }
	
    /**
    * 设置修改时间范围链式调用。
    */
    public MscAclRateQueryParam modifyDateRange(Date[] modifyDateRange) {
        setModifyDateRange(modifyDateRange);
        return this;
    }
	
    /**
    * 获取状态值: -1: 删除 0: 冻结 1: 正常。
    */
    public Integer getState(){
        return this.state;
    }

    /**
    * 设置状态值: -1: 删除 0: 冻结 1: 正常。
    */
    public void setState(Integer state){
        this.state = state;
    }
	
    /**
    * 设置状态值: -1: 删除 0: 冻结 1: 正常链式调用。
    */
    public MscAclRateQueryParam state(Integer state) {
        setState(state);
        return this;
    }

    /**
    * 获取状态值: -1: 删除 0: 冻结 1: 正常数组。
    */
    public Integer[] getStates(){
        return this.states;
    }

    /**
    * 设置状态值: -1: 删除 0: 冻结 1: 正常数组。
    */
    public void setStates(Integer[] states){
        this.states = states;
    }
	
    /**
    * 设置状态值: -1: 删除 0: 冻结 1: 正常数组链式调用。
    */
    public MscAclRateQueryParam states(Integer[] states) {
        setStates(states);
        return this;
    }
    
    /**
    * 获取大于等于状态值: -1: 删除 0: 冻结 1: 正常。
    */
    public Integer getStateGte(){
        return this.stateGte;
    }

    /**
    * 设置大于等于状态值: -1: 删除 0: 冻结 1: 正常。
    */
    public void setStateGte(Integer stateGte){
        this.stateGte = stateGte;
    }
	
    /**
    * 设置大于等于状态值: -1: 删除 0: 冻结 1: 正常链式调用。
    */
    public MscAclRateQueryParam stateGte(Integer stateGte) {
        setStateGte(stateGte);
        return this;
    }
    
    /**
    * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常。
    */
    public Integer getStateLte(){
        return this.stateLte;
    }

    /**
    * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常。
    */
    public void setStateLte(Integer stateLte){
        this.stateLte = stateLte;
    }
	
    /**
    * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常链式调用。
    */
    public MscAclRateQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }
    

}