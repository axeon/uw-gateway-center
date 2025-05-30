package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * IP访问控制列表查询参数。
 */
@Schema(title = "IP访问控制列表查询参数", description = "IP访问控制列表查询参数")
public class MscAclFilterQueryParam extends AuthPageQueryParam {

    /**
     * id。
     */
    @QueryMeta(expr = "id=?")
    @Schema(title = "id", description = "id")
    private Long id;
    /**
     * ID数组。
     */
    @QueryMeta(expr = "id in (?)")
    @Schema(title = "ID数组", description = "ID数组，可同时匹配多个。")
    private Long[] ids;
    /**
     * 用户ID。
     */
    @QueryMeta(expr = "user_id=?")
    @Schema(title = "用户ID", description = "用户ID")
    private Long userId;
    /**
     * 用户类型。
     */
    @QueryMeta(expr = "user_type=?")
    @Schema(title = "用户类型", description = "用户类型")
    private Integer userType;
    /**
     * 过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式。
     */
    @QueryMeta(expr = "filter_type=?")
    @Schema(title = "过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式", description = "过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式")
    private Integer filterType;
    /**
     * 过滤器名。
     */
    @QueryMeta(expr = "filter_name like ?")
    @Schema(title = "过滤器名", description = "过滤器名")
    private String filterName;
    /**
     * 过滤器描述。
     */
    @QueryMeta(expr = "filter_desc like ?")
    @Schema(title = "过滤器描述", description = "过滤器描述")
    private String filterDesc;
    /**
     * 申请人ID。
     */
    @QueryMeta(expr = "apply_user_id=?")
    @Schema(title = "申请人ID", description = "申请人ID")
    private Long applyUserId;
    /**
     * 申请人IP。
     */
    @QueryMeta(expr = "apply_user_ip like ?")
    @Schema(title = "申请人IP", description = "申请人IP")
    private String applyUserIp;
    /**
     * 申请人用户名。
     */
    @QueryMeta(expr = "apply_user_info like ?")
    @Schema(title = "申请人用户名", description = "申请人用户名")
    private String applyUserInfo;
    /**
     * 申请备注。
     */
    @QueryMeta(expr = "apply_remark like ?")
    @Schema(title = "申请备注", description = "申请备注")
    private String applyRemark;
    /**
     * 请求时间范围。
     */
    @QueryMeta(expr = "apply_date between ? and ?")
    @Schema(title = "请求时间范围", description = "请求时间范围")
    private Date[] applyDateRange;
    /**
     * 审批人ID。
     */
    @QueryMeta(expr = "audit_user_id=?")
    @Schema(title = "审批人ID", description = "审批人ID")
    private Long auditUserId;
    /**
     * 审批用户IP。
     */
    @QueryMeta(expr = "audit_user_ip like ?")
    @Schema(title = "审批用户IP", description = "审批用户IP")
    private String auditUserIp;
    /**
     * 审批人用户名。
     */
    @QueryMeta(expr = "audit_user_info like ?")
    @Schema(title = "审批人用户名", description = "审批人用户名")
    private String auditUserInfo;
    /**
     * 审批备注。
     */
    @QueryMeta(expr = "audit_remark like ?")
    @Schema(title = "审批备注", description = "审批备注")
    private String auditRemark;
    /**
     * 审批时间范围。
     */
    @QueryMeta(expr = "audit_date between ? and ?")
    @Schema(title = "审批时间范围", description = "审批时间范围")
    private Date[] auditDateRange;
    /**
     * 审批状态。
     */
    @QueryMeta(expr = "audit_state=?")
    @Schema(title = "审批状态", description = "审批状态")
    private Integer auditState;
    /**
     * 过期时间范围。
     */
    @QueryMeta(expr = "expire_date between ? and ?")
    @Schema(title = "过期时间范围", description = "过期时间范围")
    private Date[] expireDateRange;
    /**
     * 创建时间范围。
     */
    @QueryMeta(expr = "create_date between ? and ?")
    @Schema(title = "创建时间范围", description = "创建时间范围")
    private Date[] createDateRange;
    /**
     * 修改时间范围。
     */
    @QueryMeta(expr = "modify_date between ? and ?")
    @Schema(title = "修改时间范围", description = "修改时间范围")
    private Date[] modifyDateRange;
    /**
     * 状态值: -1: 删除 0: 冻结 1: 正常。
     */
    @QueryMeta(expr = "state=?")
    @Schema(title = "状态值: -1: 删除 0: 冻结 1: 正常", description = "状态值: -1: 删除 0: 冻结 1: 正常")
    private Integer state;
    /**
     * 状态值: -1: 删除 0: 冻结 1: 正常数组。
     */
    @QueryMeta(expr = "state in (?)")
    @Schema(title = "状态值: -1: 删除 0: 冻结 1: 正常数组", description = "状态值: -1: 删除 0: 冻结 1: 正常数组，可同时匹配多个状态。")
    private Integer[] states;
    /**
     * 大于等于状态值: -1: 删除 0: 冻结 1: 正常。
     */
    @QueryMeta(expr = "state>=?")
    @Schema(title = "大于等于状态值: -1: 删除 0: 冻结 1: 正常", description = "大于等于状态值: -1: 删除 0: 冻结 1: 正常")
    private Integer stateGte;
    /**
     * 小于等于状态值: -1: 删除 0: 冻结 1: 正常。
     */
    @QueryMeta(expr = "state<=?")
    @Schema(title = "小于等于状态值: -1: 删除 0: 冻结 1: 正常", description = "小于等于状态值: -1: 删除 0: 冻结 1: 正常")
    private Integer stateLte;
    /**
     * IP访问控制数据参数
     */
    @Schema(title = "IP访问控制数据参数", description = "IP访问控制数据参数")
    private DataQueryParam dataQueryParam;

    public MscAclFilterQueryParam() {
        super();
    }

    public MscAclFilterQueryParam(Long saasId) {
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
            put("id", "id");
            put("saasId", "saas_id");
            put("userId", "user_id");
            put("userType", "user_type");
            put("filterType", "filter_type");
            put("filterName", "filter_name");
            put("filterDesc", "filter_desc");
            put("applyUserId", "apply_user_id");
            put("applyUserIp", "apply_user_ip");
            put("applyUserInfo", "apply_user_info");
            put("applyRemark", "apply_remark");
            put("applyDate", "apply_date");
            put("auditUserId", "audit_user_id");
            put("auditUserIp", "audit_user_ip");
            put("auditUserInfo", "audit_user_info");
            put("auditRemark", "audit_remark");
            put("auditDate", "audit_date");
            put("auditState", "audit_state");
            put("expireDate", "expire_date");
            put("createDate", "create_date");
            put("modifyDate", "modify_date");
            put("state", "state");
        }};
    }

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
    public MscAclFilterQueryParam id(Long id) {
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
    public MscAclFilterQueryParam ids(Long[] ids) {
        setIds(ids);
        return this;
    }

    /**
     * 获取用户ID。
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * 设置用户ID。
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 设置用户ID链式调用。
     */
    public MscAclFilterQueryParam userId(Long userId) {
        setUserId(userId);
        return this;
    }

    /**
     * 获取用户类型。
     */
    public Integer getUserType() {
        return this.userType;
    }

    /**
     * 设置用户类型。
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 设置用户类型链式调用。
     */
    public MscAclFilterQueryParam userType(Integer userType) {
        setUserType(userType);
        return this;
    }

    /**
     * 获取过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式。
     */
    public Integer getFilterType() {
        return this.filterType;
    }

    /**
     * 设置过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式。
     */
    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    /**
     * 设置过滤器类型 -1 黑名单模式 0 不过滤 1 白名单模式链式调用。
     */
    public MscAclFilterQueryParam filterType(Integer filterType) {
        setFilterType(filterType);
        return this;
    }

    /**
     * 获取过滤器名。
     */
    public String getFilterName() {
        return this.filterName;
    }

    /**
     * 设置过滤器名。
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * 设置过滤器名链式调用。
     */
    public MscAclFilterQueryParam filterName(String filterName) {
        setFilterName(filterName);
        return this;
    }

    /**
     * 获取过滤器描述。
     */
    public String getFilterDesc() {
        return this.filterDesc;
    }

    /**
     * 设置过滤器描述。
     */
    public void setFilterDesc(String filterDesc) {
        this.filterDesc = filterDesc;
    }

    /**
     * 设置过滤器描述链式调用。
     */
    public MscAclFilterQueryParam filterDesc(String filterDesc) {
        setFilterDesc(filterDesc);
        return this;
    }

    /**
     * 获取申请人ID。
     */
    public Long getApplyUserId() {
        return this.applyUserId;
    }

    /**
     * 设置申请人ID。
     */
    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    /**
     * 设置申请人ID链式调用。
     */
    public MscAclFilterQueryParam applyUserId(Long applyUserId) {
        setApplyUserId(applyUserId);
        return this;
    }

    /**
     * 获取申请人IP。
     */
    public String getApplyUserIp() {
        return this.applyUserIp;
    }

    /**
     * 设置申请人IP。
     */
    public void setApplyUserIp(String applyUserIp) {
        this.applyUserIp = applyUserIp;
    }

    /**
     * 设置申请人IP链式调用。
     */
    public MscAclFilterQueryParam applyUserIp(String applyUserIp) {
        setApplyUserIp(applyUserIp);
        return this;
    }

    /**
     * 获取申请人用户名。
     */
    public String getApplyUserInfo() {
        return this.applyUserInfo;
    }

    /**
     * 设置申请人用户名。
     */
    public void setApplyUserInfo(String applyUserInfo) {
        this.applyUserInfo = applyUserInfo;
    }

    /**
     * 设置申请人用户名链式调用。
     */
    public MscAclFilterQueryParam applyUserInfo(String applyUserInfo) {
        setApplyUserInfo(applyUserInfo);
        return this;
    }

    /**
     * 获取申请备注。
     */
    public String getApplyRemark() {
        return this.applyRemark;
    }

    /**
     * 设置申请备注。
     */
    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    /**
     * 设置申请备注链式调用。
     */
    public MscAclFilterQueryParam applyRemark(String applyRemark) {
        setApplyRemark(applyRemark);
        return this;
    }

    /**
     * 获取请求时间范围。
     */
    public Date[] getApplyDateRange() {
        return this.applyDateRange;
    }

    /**
     * 设置请求时间范围。
     */
    public void setApplyDateRange(Date[] applyDateRange) {
        this.applyDateRange = applyDateRange;
    }

    /**
     * 设置请求时间范围链式调用。
     */
    public MscAclFilterQueryParam applyDateRange(Date[] applyDateRange) {
        setApplyDateRange(applyDateRange);
        return this;
    }

    /**
     * 获取审批人ID。
     */
    public Long getAuditUserId() {
        return this.auditUserId;
    }

    /**
     * 设置审批人ID。
     */
    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    /**
     * 设置审批人ID链式调用。
     */
    public MscAclFilterQueryParam auditUserId(Long auditUserId) {
        setAuditUserId(auditUserId);
        return this;
    }

    /**
     * 获取审批用户IP。
     */
    public String getAuditUserIp() {
        return this.auditUserIp;
    }

    /**
     * 设置审批用户IP。
     */
    public void setAuditUserIp(String auditUserIp) {
        this.auditUserIp = auditUserIp;
    }

    /**
     * 设置审批用户IP链式调用。
     */
    public MscAclFilterQueryParam auditUserIp(String auditUserIp) {
        setAuditUserIp(auditUserIp);
        return this;
    }

    /**
     * 获取审批人用户名。
     */
    public String getAuditUserInfo() {
        return this.auditUserInfo;
    }

    /**
     * 设置审批人用户名。
     */
    public void setAuditUserInfo(String auditUserInfo) {
        this.auditUserInfo = auditUserInfo;
    }

    /**
     * 设置审批人用户名链式调用。
     */
    public MscAclFilterQueryParam auditUserInfo(String auditUserInfo) {
        setAuditUserInfo(auditUserInfo);
        return this;
    }

    /**
     * 获取审批备注。
     */
    public String getAuditRemark() {
        return this.auditRemark;
    }

    /**
     * 设置审批备注。
     */
    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    /**
     * 设置审批备注链式调用。
     */
    public MscAclFilterQueryParam auditRemark(String auditRemark) {
        setAuditRemark(auditRemark);
        return this;
    }

    /**
     * 获取审批时间范围。
     */
    public Date[] getAuditDateRange() {
        return this.auditDateRange;
    }

    /**
     * 设置审批时间范围。
     */
    public void setAuditDateRange(Date[] auditDateRange) {
        this.auditDateRange = auditDateRange;
    }

    /**
     * 设置审批时间范围链式调用。
     */
    public MscAclFilterQueryParam auditDateRange(Date[] auditDateRange) {
        setAuditDateRange(auditDateRange);
        return this;
    }

    /**
     * 获取审批状态。
     */
    public Integer getAuditState() {
        return this.auditState;
    }

    /**
     * 设置审批状态。
     */
    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    /**
     * 设置审批状态链式调用。
     */
    public MscAclFilterQueryParam auditState(Integer auditState) {
        setAuditState(auditState);
        return this;
    }

    /**
     * 获取过期时间范围。
     */
    public Date[] getExpireDateRange() {
        return this.expireDateRange;
    }

    /**
     * 设置过期时间范围。
     */
    public void setExpireDateRange(Date[] expireDateRange) {
        this.expireDateRange = expireDateRange;
    }

    /**
     * 设置过期时间范围链式调用。
     */
    public MscAclFilterQueryParam expireDateRange(Date[] expireDateRange) {
        setExpireDateRange(expireDateRange);
        return this;
    }

    /**
     * 获取创建时间范围。
     */
    public Date[] getCreateDateRange() {
        return this.createDateRange;
    }

    /**
     * 设置创建时间范围。
     */
    public void setCreateDateRange(Date[] createDateRange) {
        this.createDateRange = createDateRange;
    }

    /**
     * 设置创建时间范围链式调用。
     */
    public MscAclFilterQueryParam createDateRange(Date[] createDateRange) {
        setCreateDateRange(createDateRange);
        return this;
    }

    /**
     * 获取修改时间范围。
     */
    public Date[] getModifyDateRange() {
        return this.modifyDateRange;
    }

    /**
     * 设置修改时间范围。
     */
    public void setModifyDateRange(Date[] modifyDateRange) {
        this.modifyDateRange = modifyDateRange;
    }

    /**
     * 设置修改时间范围链式调用。
     */
    public MscAclFilterQueryParam modifyDateRange(Date[] modifyDateRange) {
        setModifyDateRange(modifyDateRange);
        return this;
    }

    /**
     * 获取状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 设置状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 设置状态值: -1: 删除 0: 冻结 1: 正常链式调用。
     */
    public MscAclFilterQueryParam state(Integer state) {
        setState(state);
        return this;
    }

    /**
     * 获取状态值: -1: 删除 0: 冻结 1: 正常数组。
     */
    public Integer[] getStates() {
        return this.states;
    }

    /**
     * 设置状态值: -1: 删除 0: 冻结 1: 正常数组。
     */
    public void setStates(Integer[] states) {
        this.states = states;
    }

    /**
     * 设置状态值: -1: 删除 0: 冻结 1: 正常数组链式调用。
     */
    public MscAclFilterQueryParam states(Integer[] states) {
        setStates(states);
        return this;
    }

    /**
     * 获取大于等于状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public Integer getStateGte() {
        return this.stateGte;
    }

    /**
     * 设置大于等于状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public void setStateGte(Integer stateGte) {
        this.stateGte = stateGte;
    }

    /**
     * 设置大于等于状态值: -1: 删除 0: 冻结 1: 正常链式调用。
     */
    public MscAclFilterQueryParam stateGte(Integer stateGte) {
        setStateGte(stateGte);
        return this;
    }

    /**
     * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public Integer getStateLte() {
        return this.stateLte;
    }

    /**
     * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常。
     */
    public void setStateLte(Integer stateLte) {
        this.stateLte = stateLte;
    }

    /**
     * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常链式调用。
     */
    public MscAclFilterQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }

    /**
     * 获取数据查询参数。
     * @return
     */
    public DataQueryParam getDataQueryParam() {
        return dataQueryParam;
    }

    /**
     * 设置数据查询参数。
     * @param dataQueryParam
     */
    public void setDataQueryParam(DataQueryParam dataQueryParam) {
        this.dataQueryParam = dataQueryParam;
    }

    /**
     * 设置数据查询参数。
     * @param dataQueryParam
     * @return
     */
    public MscAclFilterQueryParam dataQueryParam(DataQueryParam dataQueryParam) {
        setDataQueryParam(dataQueryParam);
        return this;
    }

    /**
     * IP访问控制数据列表查询参数。
     */
    @Schema(title = "IP访问控制数据列表查询参数", description = "IP访问控制数据列表查询参数")
    public class DataQueryParam extends AuthPageQueryParam {

        /**
         * id。
         */
        @QueryMeta(expr = "id=?")
        @Schema(title = "id", description = "id")
        private Long id;
        /**
         * ID数组。
         */
        @QueryMeta(expr = "id in (?)")
        @Schema(title = "ID数组", description = "ID数组，可同时匹配多个。")
        private Long[] ids;
        /**
         * 过滤器id。
         */
        @QueryMeta(expr = "filter_id=?")
        @Schema(title = "过滤器id", description = "过滤器id")
        private Long filterId;
        /**
         * ip信息。
         */
        @QueryMeta(expr = "ip_info like ?")
        @Schema(title = "ip信息", description = "ip信息")
        private String ipInfo;
        /**
         * ip开始。
         */
        @QueryMeta(expr = "ip_start like ?")
        @Schema(title = "ip开始", description = "ip开始")
        private String ipStart;
        /**
         * ip结束。
         */
        @QueryMeta(expr = "ip_end like ?")
        @Schema(title = "ip结束", description = "ip结束")
        private String ipEnd;
        /**
         * 申请人ID。
         */
        @QueryMeta(expr = "apply_user_id=?")
        @Schema(title = "申请人ID", description = "申请人ID")
        private Long applyUserId;
        /**
         * 申请人IP。
         */
        @QueryMeta(expr = "apply_user_ip like ?")
        @Schema(title = "申请人IP", description = "申请人IP")
        private String applyUserIp;
        /**
         * 申请人用户名。
         */
        @QueryMeta(expr = "apply_user_info like ?")
        @Schema(title = "申请人用户名", description = "申请人用户名")
        private String applyUserInfo;
        /**
         * 申请备注。
         */
        @QueryMeta(expr = "apply_remark like ?")
        @Schema(title = "申请备注", description = "申请备注")
        private String applyRemark;
        /**
         * 请求时间范围。
         */
        @QueryMeta(expr = "apply_date between ? and ?")
        @Schema(title = "请求时间范围", description = "请求时间范围")
        private Date[] applyDateRange;
        /**
         * 审批人ID。
         */
        @QueryMeta(expr = "audit_user_id=?")
        @Schema(title = "审批人ID", description = "审批人ID")
        private Long auditUserId;
        /**
         * 审批用户IP。
         */
        @QueryMeta(expr = "audit_user_ip like ?")
        @Schema(title = "审批用户IP", description = "审批用户IP")
        private String auditUserIp;
        /**
         * 审批人用户名。
         */
        @QueryMeta(expr = "audit_user_info like ?")
        @Schema(title = "审批人用户名", description = "审批人用户名")
        private String auditUserInfo;
        /**
         * 审批备注。
         */
        @QueryMeta(expr = "audit_remark like ?")
        @Schema(title = "审批备注", description = "审批备注")
        private String auditRemark;
        /**
         * 审批时间范围。
         */
        @QueryMeta(expr = "audit_date between ? and ?")
        @Schema(title = "审批时间范围", description = "审批时间范围")
        private Date[] auditDateRange;
        /**
         * 审批状态。
         */
        @QueryMeta(expr = "audit_state=?")
        @Schema(title = "审批状态", description = "审批状态")
        private Integer auditState;
        /**
         * 过期时间范围。
         */
        @QueryMeta(expr = "expire_date between ? and ?")
        @Schema(title = "过期时间范围", description = "过期时间范围")
        private Date[] expireDateRange;
        /**
         * 创建时间范围。
         */
        @QueryMeta(expr = "create_date between ? and ?")
        @Schema(title = "创建时间范围", description = "创建时间范围")
        private Date[] createDateRange;
        /**
         * 修改时间范围。
         */
        @QueryMeta(expr = "modify_date between ? and ?")
        @Schema(title = "修改时间范围", description = "修改时间范围")
        private Date[] modifyDateRange;
        /**
         * 状态值: -1: 删除 0: 冻结 1: 正常。
         */
        @QueryMeta(expr = "state=?")
        @Schema(title = "状态值: -1: 删除 0: 冻结 1: 正常", description = "状态值: -1: 删除 0: 冻结 1: 正常")
        private Integer state;
        /**
         * 状态值: -1: 删除 0: 冻结 1: 正常数组。
         */
        @QueryMeta(expr = "state in (?)")
        @Schema(title = "状态值: -1: 删除 0: 冻结 1: 正常数组", description = "状态值: -1: 删除 0: 冻结 1: 正常数组，可同时匹配多个状态。")
        private Integer[] states;
        /**
         * 大于等于状态值: -1: 删除 0: 冻结 1: 正常。
         */
        @QueryMeta(expr = "state>=?")
        @Schema(title = "大于等于状态值: -1: 删除 0: 冻结 1: 正常", description = "大于等于状态值: -1: 删除 0: 冻结 1: 正常")
        private Integer stateGte;
        /**
         * 小于等于状态值: -1: 删除 0: 冻结 1: 正常。
         */
        @QueryMeta(expr = "state<=?")
        @Schema(title = "小于等于状态值: -1: 删除 0: 冻结 1: 正常", description = "小于等于状态值: -1: 删除 0: 冻结 1: 正常")
        private Integer stateLte;

        public DataQueryParam() {
            super();
        }

        public DataQueryParam(Long saasId) {
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
                put("id", "id");
                put("saasId", "saas_id");
                put("filterId", "filter_id");
                put("ipInfo", "ip_info");
                put("ipStart", "ip_start");
                put("ipEnd", "ip_end");
                put("applyUserId", "apply_user_id");
                put("applyUserIp", "apply_user_ip");
                put("applyUserInfo", "apply_user_info");
                put("applyRemark", "apply_remark");
                put("applyDate", "apply_date");
                put("auditUserId", "audit_user_id");
                put("auditUserIp", "audit_user_ip");
                put("auditUserInfo", "audit_user_info");
                put("auditRemark", "audit_remark");
                put("auditDate", "audit_date");
                put("auditState", "audit_state");
                put("expireDate", "expire_date");
                put("createDate", "create_date");
                put("modifyDate", "modify_date");
                put("state", "state");
            }};
        }

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
        public DataQueryParam id(Long id) {
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
        public DataQueryParam ids(Long[] ids) {
            setIds(ids);
            return this;
        }

        /**
         * 获取过滤器id。
         */
        public Long getFilterId() {
            return this.filterId;
        }

        /**
         * 设置过滤器id。
         */
        public void setFilterId(Long filterId) {
            this.filterId = filterId;
        }

        /**
         * 设置过滤器id链式调用。
         */
        public DataQueryParam filterId(Long filterId) {
            setFilterId(filterId);
            return this;
        }

        /**
         * 获取ip信息。
         */
        public String getIpInfo() {
            return this.ipInfo;
        }

        /**
         * 设置ip信息。
         */
        public void setIpInfo(String ipInfo) {
            this.ipInfo = ipInfo;
        }

        /**
         * 设置ip信息链式调用。
         */
        public DataQueryParam ipInfo(String ipInfo) {
            setIpInfo(ipInfo);
            return this;
        }

        /**
         * 获取ip开始。
         */
        public String getIpStart() {
            return this.ipStart;
        }

        /**
         * 设置ip开始。
         */
        public void setIpStart(String ipStart) {
            this.ipStart = ipStart;
        }

        /**
         * 设置ip开始链式调用。
         */
        public DataQueryParam ipStart(String ipStart) {
            setIpStart(ipStart);
            return this;
        }

        /**
         * 获取ip结束。
         */
        public String getIpEnd() {
            return this.ipEnd;
        }

        /**
         * 设置ip结束。
         */
        public void setIpEnd(String ipEnd) {
            this.ipEnd = ipEnd;
        }

        /**
         * 设置ip结束链式调用。
         */
        public DataQueryParam ipEnd(String ipEnd) {
            setIpEnd(ipEnd);
            return this;
        }

        /**
         * 获取申请人ID。
         */
        public Long getApplyUserId() {
            return this.applyUserId;
        }

        /**
         * 设置申请人ID。
         */
        public void setApplyUserId(Long applyUserId) {
            this.applyUserId = applyUserId;
        }

        /**
         * 设置申请人ID链式调用。
         */
        public DataQueryParam applyUserId(Long applyUserId) {
            setApplyUserId(applyUserId);
            return this;
        }

        /**
         * 获取申请人IP。
         */
        public String getApplyUserIp() {
            return this.applyUserIp;
        }

        /**
         * 设置申请人IP。
         */
        public void setApplyUserIp(String applyUserIp) {
            this.applyUserIp = applyUserIp;
        }

        /**
         * 设置申请人IP链式调用。
         */
        public DataQueryParam applyUserIp(String applyUserIp) {
            setApplyUserIp(applyUserIp);
            return this;
        }

        /**
         * 获取申请人用户名。
         */
        public String getApplyUserInfo() {
            return this.applyUserInfo;
        }

        /**
         * 设置申请人用户名。
         */
        public void setApplyUserInfo(String applyUserInfo) {
            this.applyUserInfo = applyUserInfo;
        }

        /**
         * 设置申请人用户名链式调用。
         */
        public DataQueryParam applyUserInfo(String applyUserInfo) {
            setApplyUserInfo(applyUserInfo);
            return this;
        }

        /**
         * 获取申请备注。
         */
        public String getApplyRemark() {
            return this.applyRemark;
        }

        /**
         * 设置申请备注。
         */
        public void setApplyRemark(String applyRemark) {
            this.applyRemark = applyRemark;
        }

        /**
         * 设置申请备注链式调用。
         */
        public DataQueryParam applyRemark(String applyRemark) {
            setApplyRemark(applyRemark);
            return this;
        }

        /**
         * 获取请求时间范围。
         */
        public Date[] getApplyDateRange() {
            return this.applyDateRange;
        }

        /**
         * 设置请求时间范围。
         */
        public void setApplyDateRange(Date[] applyDateRange) {
            this.applyDateRange = applyDateRange;
        }

        /**
         * 设置请求时间范围链式调用。
         */
        public DataQueryParam applyDateRange(Date[] applyDateRange) {
            setApplyDateRange(applyDateRange);
            return this;
        }

        /**
         * 获取审批人ID。
         */
        public Long getAuditUserId() {
            return this.auditUserId;
        }

        /**
         * 设置审批人ID。
         */
        public void setAuditUserId(Long auditUserId) {
            this.auditUserId = auditUserId;
        }

        /**
         * 设置审批人ID链式调用。
         */
        public DataQueryParam auditUserId(Long auditUserId) {
            setAuditUserId(auditUserId);
            return this;
        }

        /**
         * 获取审批用户IP。
         */
        public String getAuditUserIp() {
            return this.auditUserIp;
        }

        /**
         * 设置审批用户IP。
         */
        public void setAuditUserIp(String auditUserIp) {
            this.auditUserIp = auditUserIp;
        }

        /**
         * 设置审批用户IP链式调用。
         */
        public DataQueryParam auditUserIp(String auditUserIp) {
            setAuditUserIp(auditUserIp);
            return this;
        }

        /**
         * 获取审批人用户名。
         */
        public String getAuditUserInfo() {
            return this.auditUserInfo;
        }

        /**
         * 设置审批人用户名。
         */
        public void setAuditUserInfo(String auditUserInfo) {
            this.auditUserInfo = auditUserInfo;
        }

        /**
         * 设置审批人用户名链式调用。
         */
        public DataQueryParam auditUserInfo(String auditUserInfo) {
            setAuditUserInfo(auditUserInfo);
            return this;
        }

        /**
         * 获取审批备注。
         */
        public String getAuditRemark() {
            return this.auditRemark;
        }

        /**
         * 设置审批备注。
         */
        public void setAuditRemark(String auditRemark) {
            this.auditRemark = auditRemark;
        }

        /**
         * 设置审批备注链式调用。
         */
        public DataQueryParam auditRemark(String auditRemark) {
            setAuditRemark(auditRemark);
            return this;
        }

        /**
         * 获取审批时间范围。
         */
        public Date[] getAuditDateRange() {
            return this.auditDateRange;
        }

        /**
         * 设置审批时间范围。
         */
        public void setAuditDateRange(Date[] auditDateRange) {
            this.auditDateRange = auditDateRange;
        }

        /**
         * 设置审批时间范围链式调用。
         */
        public DataQueryParam auditDateRange(Date[] auditDateRange) {
            setAuditDateRange(auditDateRange);
            return this;
        }

        /**
         * 获取审批状态。
         */
        public Integer getAuditState() {
            return this.auditState;
        }

        /**
         * 设置审批状态。
         */
        public void setAuditState(Integer auditState) {
            this.auditState = auditState;
        }

        /**
         * 设置审批状态链式调用。
         */
        public DataQueryParam auditState(Integer auditState) {
            setAuditState(auditState);
            return this;
        }

        /**
         * 获取过期时间范围。
         */
        public Date[] getExpireDateRange() {
            return this.expireDateRange;
        }

        /**
         * 设置过期时间范围。
         */
        public void setExpireDateRange(Date[] expireDateRange) {
            this.expireDateRange = expireDateRange;
        }

        /**
         * 设置过期时间范围链式调用。
         */
        public DataQueryParam expireDateRange(Date[] expireDateRange) {
            setExpireDateRange(expireDateRange);
            return this;
        }

        /**
         * 获取创建时间范围。
         */
        public Date[] getCreateDateRange() {
            return this.createDateRange;
        }

        /**
         * 设置创建时间范围。
         */
        public void setCreateDateRange(Date[] createDateRange) {
            this.createDateRange = createDateRange;
        }

        /**
         * 设置创建时间范围链式调用。
         */
        public uw.gateway.center.dto.MscAclFilterQueryParam.DataQueryParam createDateRange(Date[] createDateRange) {
            setCreateDateRange(createDateRange);
            return this;
        }

        /**
         * 获取修改时间范围。
         */
        public Date[] getModifyDateRange() {
            return this.modifyDateRange;
        }

        /**
         * 设置修改时间范围。
         */
        public void setModifyDateRange(Date[] modifyDateRange) {
            this.modifyDateRange = modifyDateRange;
        }

        /**
         * 设置修改时间范围链式调用。
         */
        public DataQueryParam modifyDateRange(Date[] modifyDateRange) {
            setModifyDateRange(modifyDateRange);
            return this;
        }

        /**
         * 获取状态值: -1: 删除 0: 冻结 1: 正常。
         */
        public Integer getState() {
            return this.state;
        }

        /**
         * 设置状态值: -1: 删除 0: 冻结 1: 正常。
         */
        public void setState(Integer state) {
            this.state = state;
        }

        /**
         * 设置状态值: -1: 删除 0: 冻结 1: 正常链式调用。
         */
        public DataQueryParam state(Integer state) {
            setState(state);
            return this;
        }

        /**
         * 获取状态值: -1: 删除 0: 冻结 1: 正常数组。
         */
        public Integer[] getStates() {
            return this.states;
        }

        /**
         * 设置状态值: -1: 删除 0: 冻结 1: 正常数组。
         */
        public void setStates(Integer[] states) {
            this.states = states;
        }

        /**
         * 设置状态值: -1: 删除 0: 冻结 1: 正常数组链式调用。
         */
        public DataQueryParam states(Integer[] states) {
            setStates(states);
            return this;
        }

        /**
         * 获取大于等于状态值: -1: 删除 0: 冻结 1: 正常。
         */
        public Integer getStateGte() {
            return this.stateGte;
        }

        /**
         * 设置大于等于状态值: -1: 删除 0: 冻结 1: 正常。
         */
        public void setStateGte(Integer stateGte) {
            this.stateGte = stateGte;
        }

        /**
         * 设置大于等于状态值: -1: 删除 0: 冻结 1: 正常链式调用。
         */
        public DataQueryParam stateGte(Integer stateGte) {
            setStateGte(stateGte);
            return this;
        }

        /**
         * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常。
         */
        public Integer getStateLte() {
            return this.stateLte;
        }

        /**
         * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常。
         */
        public void setStateLte(Integer stateLte) {
            this.stateLte = stateLte;
        }

        /**
         * 获取小于等于状态值: -1: 删除 0: 冻结 1: 正常链式调用。
         */
        public DataQueryParam stateLte(Integer stateLte) {
            setStateLte(stateLte);
            return this;
        }


    }

}