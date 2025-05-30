package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* acme域名列表查询参数。
*/
@Schema(title = "acme域名列表查询参数", description = "acme域名列表查询参数")
public class MscAcmeDomainQueryParam extends AuthPageQueryParam{

    public MscAcmeDomainQueryParam() {
        super();
    }

    public MscAcmeDomainQueryParam(Long saasId) {
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
            put( "domainName", "domain_name" );
            put( "domainCertAlg", "domain_cert_alg" );
            put( "acmeVendor", "acme_vendor" );
            put( "dnsVendor", "dns_vendor" );
            put( "lastUpdate", "last_update" );
            put( "lastActiveDate", "last_active_date" );
            put( "lastExpireDate", "last_expire_date" );
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
    * 域名名称。
    */
    @QueryMeta(expr = "domain_name like ?")
    @Schema(title="域名名称", description = "域名名称")
    private String domainName;
	
    /**
    * 域名证书算法。
    */
    @QueryMeta(expr = "domain_cert_alg like ?")
    @Schema(title="域名证书算法", description = "域名证书算法")
    private String domainCertAlg;
	
    /**
    * acme服务商。
    */
    @QueryMeta(expr = "acme_vendor like ?")
    @Schema(title="acme服务商", description = "acme服务商")
    private String acmeVendor;
	
    /**
    * dns供应商。
    */
    @QueryMeta(expr = "dns_vendor like ?")
    @Schema(title="dns供应商", description = "dns供应商")
    private String dnsVendor;
	
    /**
    * 上次更新时间范围。
    */
    @QueryMeta(expr = "last_update between ? and ?")
    @Schema(title="上次更新时间范围", description = "上次更新时间范围")
    private Date[] lastUpdateRange;

    /**
    * 上次更新时间范围。
    */
    @QueryMeta(expr = "last_active_date between ? and ?")
    @Schema(title="上次更新时间范围", description = "上次更新时间范围")
    private Date[] lastActiveDateRange;

    /**
    * 最近证书过期时间范围。
    */
    @QueryMeta(expr = "last_expire_date between ? and ?")
    @Schema(title="最近证书过期时间范围", description = "最近证书过期时间范围")
    private Date[] lastExpireDateRange;

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
    public MscAcmeDomainQueryParam id(Long id) {
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
    public MscAcmeDomainQueryParam ids(Long[] ids) {
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
	public MscAcmeDomainQueryParam accountId(Long accountId){
        setAccountId(accountId);
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
    public MscAcmeDomainQueryParam domainName(String domainName) {
        setDomainName(domainName);
        return this;
    }
	
    /**
    * 获取域名证书算法。
    */
    public String getDomainCertAlg(){
        return this.domainCertAlg;
    }

    /**
    * 设置域名证书算法。
    */
    public void setDomainCertAlg(String domainCertAlg){
        this.domainCertAlg = domainCertAlg;
    }
	
    /**
    * 设置域名证书算法链式调用。
    */
    public MscAcmeDomainQueryParam domainCertAlg(String domainCertAlg) {
        setDomainCertAlg(domainCertAlg);
        return this;
    }
	
    /**
    * 获取acme服务商。
    */
    public String getAcmeVendor(){
        return this.acmeVendor;
    }

    /**
    * 设置acme服务商。
    */
    public void setAcmeVendor(String acmeVendor){
        this.acmeVendor = acmeVendor;
    }
	
    /**
    * 设置acme服务商链式调用。
    */
    public MscAcmeDomainQueryParam acmeVendor(String acmeVendor) {
        setAcmeVendor(acmeVendor);
        return this;
    }
	
    /**
    * 获取dns供应商。
    */
    public String getDnsVendor(){
        return this.dnsVendor;
    }

    /**
    * 设置dns供应商。
    */
    public void setDnsVendor(String dnsVendor){
        this.dnsVendor = dnsVendor;
    }
	
    /**
    * 设置dns供应商链式调用。
    */
    public MscAcmeDomainQueryParam dnsVendor(String dnsVendor) {
        setDnsVendor(dnsVendor);
        return this;
    }
	
    /**
    * 获取上次更新时间范围。
    */
    public Date[] getLastUpdateRange(){
        return this.lastUpdateRange;
    }

    /**
    * 设置上次更新时间范围。
    */
    public void setLastUpdateRange(Date[] lastUpdateRange){
        this.lastUpdateRange = lastUpdateRange;
    }
	
    /**
    * 设置上次更新时间范围链式调用。
    */
    public MscAcmeDomainQueryParam lastUpdateRange(Date[] lastUpdateRange) {
        setLastUpdateRange(lastUpdateRange);
        return this;
    }
	
    /**
    * 获取上次更新时间范围。
    */
    public Date[] getLastActiveDateRange(){
        return this.lastActiveDateRange;
    }

    /**
    * 设置上次更新时间范围。
    */
    public void setLastActiveDateRange(Date[] lastActiveDateRange){
        this.lastActiveDateRange = lastActiveDateRange;
    }
	
    /**
    * 设置上次更新时间范围链式调用。
    */
    public MscAcmeDomainQueryParam lastActiveDateRange(Date[] lastActiveDateRange) {
        setLastActiveDateRange(lastActiveDateRange);
        return this;
    }
	
    /**
    * 获取最近证书过期时间范围。
    */
    public Date[] getLastExpireDateRange(){
        return this.lastExpireDateRange;
    }

    /**
    * 设置最近证书过期时间范围。
    */
    public void setLastExpireDateRange(Date[] lastExpireDateRange){
        this.lastExpireDateRange = lastExpireDateRange;
    }
	
    /**
    * 设置最近证书过期时间范围链式调用。
    */
    public MscAcmeDomainQueryParam lastExpireDateRange(Date[] lastExpireDateRange) {
        setLastExpireDateRange(lastExpireDateRange);
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
    public MscAcmeDomainQueryParam createDateRange(Date[] createDateRange) {
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
    public MscAcmeDomainQueryParam modifyDateRange(Date[] modifyDateRange) {
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
    public MscAcmeDomainQueryParam state(Integer state) {
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
    public MscAcmeDomainQueryParam states(Integer[] states) {
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
    public MscAcmeDomainQueryParam stateGte(Integer stateGte) {
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
    public MscAcmeDomainQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }
    

}