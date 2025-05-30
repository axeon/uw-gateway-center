package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* acme部署日志列表查询参数。
*/
@Schema(title = "acme部署日志列表查询参数", description = "acme部署日志列表查询参数")
public class MscAcmeDeployLogQueryParam extends AuthPageQueryParam{

    public MscAcmeDeployLogQueryParam() {
        super();
    }

    public MscAcmeDeployLogQueryParam(Long saasId) {
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
            put( "certId", "cert_id" );
            put( "deployId", "deploy_id" );
            put( "deployDate", "deploy_date" );
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
    * 证书id。
    */
    @QueryMeta(expr = "cert_id=?")
    @Schema(title="证书id", description = "证书id")
    private Long certId;
	
    /**
    * 部署id。
    */
    @QueryMeta(expr = "deploy_id=?")
    @Schema(title="部署id", description = "部署id")
    private Long deployId;
	
    /**
    * 部署时间范围。
    */
    @QueryMeta(expr = "deploy_date between ? and ?")
    @Schema(title="部署时间范围", description = "部署时间范围")
    private Date[] deployDateRange;

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
    public MscAcmeDeployLogQueryParam id(Long id) {
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
    public MscAcmeDeployLogQueryParam ids(Long[] ids) {
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
	public MscAcmeDeployLogQueryParam domainId(Long domainId){
        setDomainId(domainId);
        return this;
    }
	
    /**
    * 获取证书id。
    */
    public Long getCertId(){
        return this.certId;
    }

    /**
    * 设置证书id。
    */
    public void setCertId(Long certId){
        this.certId = certId;
    }
	
    /**
    * 设置证书id链式调用。
    */
	public MscAcmeDeployLogQueryParam certId(Long certId){
        setCertId(certId);
        return this;
    }
	
    /**
    * 获取部署id。
    */
    public Long getDeployId(){
        return this.deployId;
    }

    /**
    * 设置部署id。
    */
    public void setDeployId(Long deployId){
        this.deployId = deployId;
    }
	
    /**
    * 设置部署id链式调用。
    */
	public MscAcmeDeployLogQueryParam deployId(Long deployId){
        setDeployId(deployId);
        return this;
    }
	
    /**
    * 获取部署时间范围。
    */
    public Date[] getDeployDateRange(){
        return this.deployDateRange;
    }

    /**
    * 设置部署时间范围。
    */
    public void setDeployDateRange(Date[] deployDateRange){
        this.deployDateRange = deployDateRange;
    }
	
    /**
    * 设置部署时间范围链式调用。
    */
    public MscAcmeDeployLogQueryParam deployDateRange(Date[] deployDateRange) {
        setDeployDateRange(deployDateRange);
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
    public MscAcmeDeployLogQueryParam state(Integer state) {
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
    public MscAcmeDeployLogQueryParam states(Integer[] states) {
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
    public MscAcmeDeployLogQueryParam stateGte(Integer stateGte) {
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
    public MscAcmeDeployLogQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }
    

}