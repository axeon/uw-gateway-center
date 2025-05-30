package uw.gateway.center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.dto.AuthPageQueryParam;
import uw.dao.annotation.QueryMeta;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* acme账号列表查询参数。
*/
@Schema(title = "acme账号列表查询参数", description = "acme账号列表查询参数")
public class MscAcmeAccountQueryParam extends AuthPageQueryParam{

    public MscAcmeAccountQueryParam() {
        super();
    }

    public MscAcmeAccountQueryParam(Long saasId) {
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
            put( "accountName", "account_name" );
            put( "accountCertAlg", "account_cert_alg" );
            put( "eabId", "eab_id" );
            put( "eabKey", "eab_key" );
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
    * 账号名称。
    */
    @QueryMeta(expr = "account_name like ?")
    @Schema(title="账号名称", description = "账号名称")
    private String accountName;
	
    /**
    * 账户证书算法。
    */
    @QueryMeta(expr = "account_cert_alg like ?")
    @Schema(title="账户证书算法", description = "账户证书算法")
    private String accountCertAlg;
	
    /**
    * EAB KID。
    */
    @QueryMeta(expr = "eab_id like ?")
    @Schema(title="EAB KID", description = "EAB KID")
    private String eabId;
	
    /**
    * EAB-KEY。
    */
    @QueryMeta(expr = "eab_key like ?")
    @Schema(title="EAB-KEY", description = "EAB-KEY")
    private String eabKey;
	
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
    public MscAcmeAccountQueryParam id(Long id) {
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
    public MscAcmeAccountQueryParam ids(Long[] ids) {
        setIds(ids);
        return this;
    }

    /**
    * 获取账号名称。
    */
    public String getAccountName(){
        return this.accountName;
    }

    /**
    * 设置账号名称。
    */
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }
	
    /**
    * 设置账号名称链式调用。
    */
    public MscAcmeAccountQueryParam accountName(String accountName) {
        setAccountName(accountName);
        return this;
    }
	
    /**
    * 获取账户证书算法。
    */
    public String getAccountCertAlg(){
        return this.accountCertAlg;
    }

    /**
    * 设置账户证书算法。
    */
    public void setAccountCertAlg(String accountCertAlg){
        this.accountCertAlg = accountCertAlg;
    }
	
    /**
    * 设置账户证书算法链式调用。
    */
    public MscAcmeAccountQueryParam accountCertAlg(String accountCertAlg) {
        setAccountCertAlg(accountCertAlg);
        return this;
    }
	
    /**
    * 获取EAB KID。
    */
    public String getEabId(){
        return this.eabId;
    }

    /**
    * 设置EAB KID。
    */
    public void setEabId(String eabId){
        this.eabId = eabId;
    }
	
    /**
    * 设置EAB KID链式调用。
    */
    public MscAcmeAccountQueryParam eabId(String eabId) {
        setEabId(eabId);
        return this;
    }
	
    /**
    * 获取EAB-KEY。
    */
    public String getEabKey(){
        return this.eabKey;
    }

    /**
    * 设置EAB-KEY。
    */
    public void setEabKey(String eabKey){
        this.eabKey = eabKey;
    }
	
    /**
    * 设置EAB-KEY链式调用。
    */
    public MscAcmeAccountQueryParam eabKey(String eabKey) {
        setEabKey(eabKey);
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
    public MscAcmeAccountQueryParam createDateRange(Date[] createDateRange) {
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
    public MscAcmeAccountQueryParam modifyDateRange(Date[] modifyDateRange) {
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
    public MscAcmeAccountQueryParam state(Integer state) {
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
    public MscAcmeAccountQueryParam states(Integer[] states) {
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
    public MscAcmeAccountQueryParam stateGte(Integer stateGte) {
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
    public MscAcmeAccountQueryParam stateLte(Integer stateLte) {
        setStateLte(stateLte);
        return this;
    }
    

}