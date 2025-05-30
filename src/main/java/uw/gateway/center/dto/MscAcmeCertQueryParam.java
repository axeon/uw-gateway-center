package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* acme证书列表查询参数。
*/
@Schema(title = "acme证书列表查询参数", description = "acme证书列表查询参数")
public class MscAcmeCertQueryParam extends AuthPageQueryParam{

    public MscAcmeCertQueryParam() {
        super();
    }

    public MscAcmeCertQueryParam(Long saasId) {
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
            put( "accountId", "account_id" );
            put( "domainId", "domain_id" );
            put( "domainName", "domain_name" );
            put( "certAlg", "cert_alg" );
            put( "activeDate", "active_date" );
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
    * 账户ID。
    */
    @QueryMeta(expr = "account_id=?")
    @Schema(title="账户ID", description = "账户ID")
    private Long accountId;
	
    /**
    * 域名id。
    */
    @QueryMeta(expr = "domain_id=?")
    @Schema(title="域名id", description = "域名id")
    private Long domainId;
	
    /**
    * 域名名称。
    */
    @QueryMeta(expr = "domain_name like ?")
    @Schema(title="域名名称", description = "域名名称")
    private String domainName;
	
    /**
    * 证书算法。
    */
    @QueryMeta(expr = "cert_alg like ?")
    @Schema(title="证书算法", description = "证书算法")
    private String certAlg;
	
    /**
    * 激活日期范围。
    */
    @QueryMeta(expr = "active_date between ? and ?")
    @Schema(title="激活日期范围", description = "激活日期范围")
    private Date[] activeDateRange;

    /**
    * 失效时间范围。
    */
    @QueryMeta(expr = "expire_date between ? and ?")
    @Schema(title="失效时间范围", description = "失效时间范围")
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
    public MscAcmeCertQueryParam id(Long id) {
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
    public MscAcmeCertQueryParam ids(Long[] ids) {
        setIds(ids);
        return this;
    }

    /**
    * 获取账户ID。
    */
    public Long getAccountId(){
        return this.accountId;
    }

    /**
    * 设置账户ID。
    */
    public void setAccountId(Long accountId){
        this.accountId = accountId;
    }
	
    /**
    * 设置账户ID链式调用。
    */
	public MscAcmeCertQueryParam accountId(Long accountId){
        setAccountId(accountId);
        return this;
    }
	
    /**
    * 获取域名id。
    */
    public Long getDomainId(){
        return this.domainId;
    }

    /**
    * 设置域名id。
    */
    public void setDomainId(Long domainId){
        this.domainId = domainId;
    }
	
    /**
    * 设置域名id链式调用。
    */
	public MscAcmeCertQueryParam domainId(Long domainId){
        setDomainId(domainId);
        return this;
    }
	
    /**
    * 获取域名名称。
    */
    public String getDomainName(){
        return this.domainName;
    }

    /**
    * 设置域名名称。
    */
    public void setDomainName(String domainName){
        this.domainName = domainName;
    }
	
    /**
    * 设置域名名称链式调用。
    */
    public MscAcmeCertQueryParam domainName(String domainName) {
        setDomainName(domainName);
        return this;
    }
	
    /**
    * 获取证书算法。
    */
    public String getCertAlg(){
        return this.certAlg;
    }

    /**
    * 设置证书算法。
    */
    public void setCertAlg(String certAlg){
        this.certAlg = certAlg;
    }
	
    /**
    * 设置证书算法链式调用。
    */
    public MscAcmeCertQueryParam certAlg(String certAlg) {
        setCertAlg(certAlg);
        return this;
    }
	
    /**
    * 获取激活日期范围。
    */
    public Date[] getActiveDateRange(){
        return this.activeDateRange;
    }

    /**
    * 设置激活日期范围。
    */
    public void setActiveDateRange(Date[] activeDateRange){
        this.activeDateRange = activeDateRange;
    }
	
    /**
    * 设置激活日期范围链式调用。
    */
    public MscAcmeCertQueryParam activeDateRange(Date[] activeDateRange) {
        setActiveDateRange(activeDateRange);
        return this;
    }
	
    /**
    * 获取失效时间范围。
    */
    public Date[] getExpireDateRange(){
        return this.expireDateRange;
    }

    /**
    * 设置失效时间范围。
    */
    public void setExpireDateRange(Date[] expireDateRange){
        this.expireDateRange = expireDateRange;
    }
	
    /**
    * 设置失效时间范围链式调用。
    */
    public MscAcmeCertQueryParam expireDateRange(Date[] expireDateRange) {
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
    public MscAcmeCertQueryParam createDateRange(Date[] createDateRange) {
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
    public MscAcmeCertQueryParam modifyDateRange(Date[] modifyDateRange) {
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
    public MscAcmeCertQueryParam state(Integer state) {
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
    public MscAcmeCertQueryParam states(Integer[] states) {
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
    public MscAcmeCertQueryParam stateGte(Integer stateGte) {
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
    public MscAcmeCertQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }
    

}