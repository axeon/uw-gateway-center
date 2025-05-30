package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* acme部署列表查询参数。
*/
@Schema(title = "acme部署列表查询参数", description = "acme部署列表查询参数")
public class MscAcmeDeployQueryParam extends AuthPageQueryParam{

    public MscAcmeDeployQueryParam() {
        super();
    }

    public MscAcmeDeployQueryParam(Long saasId) {
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
            put( "domainId", "domain_id" );
            put( "deployName", "deploy_name" );
            put( "deployVendor", "deploy_vendor" );
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
    * 域名id。
    */
    @QueryMeta(expr = "domain_id=?")
    @Schema(title="域名id", description = "域名id")
    private Long domainId;
	
    /**
    * 部署名称。
    */
    @QueryMeta(expr = "deploy_name like ?")
    @Schema(title="部署名称", description = "部署名称")
    private String deployName;
	
    /**
    * 部署目标。
    */
    @QueryMeta(expr = "deploy_vendor like ?")
    @Schema(title="部署目标", description = "部署目标")
    private String deployVendor;
	
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
    public MscAcmeDeployQueryParam id(Long id) {
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
    public MscAcmeDeployQueryParam ids(Long[] ids) {
        setIds(ids);
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
	public MscAcmeDeployQueryParam domainId(Long domainId){
        setDomainId(domainId);
        return this;
    }
	
    /**
    * 获取部署名称。
    */
    public String getDeployName(){
        return this.deployName;
    }

    /**
    * 设置部署名称。
    */
    public void setDeployName(String deployName){
        this.deployName = deployName;
    }
	
    /**
    * 设置部署名称链式调用。
    */
    public MscAcmeDeployQueryParam deployName(String deployName) {
        setDeployName(deployName);
        return this;
    }
	
    /**
    * 获取部署目标。
    */
    public String getDeployVendor(){
        return this.deployVendor;
    }

    /**
    * 设置部署目标。
    */
    public void setDeployVendor(String deployVendor){
        this.deployVendor = deployVendor;
    }
	
    /**
    * 设置部署目标链式调用。
    */
    public MscAcmeDeployQueryParam deployVendor(String deployVendor) {
        setDeployVendor(deployVendor);
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
    public MscAcmeDeployQueryParam lastUpdateRange(Date[] lastUpdateRange) {
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
    public MscAcmeDeployQueryParam lastActiveDateRange(Date[] lastActiveDateRange) {
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
    public MscAcmeDeployQueryParam lastExpireDateRange(Date[] lastExpireDateRange) {
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
    public MscAcmeDeployQueryParam createDateRange(Date[] createDateRange) {
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
    public MscAcmeDeployQueryParam modifyDateRange(Date[] modifyDateRange) {
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
    public MscAcmeDeployQueryParam state(Integer state) {
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
    public MscAcmeDeployQueryParam states(Integer[] states) {
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
    public MscAcmeDeployQueryParam stateGte(Integer stateGte) {
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
    public MscAcmeDeployQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }
    

}