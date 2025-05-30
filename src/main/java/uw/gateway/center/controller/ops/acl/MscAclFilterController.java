package uw.gateway.center.controller.ops.acl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.gateway.center.acl.MscAclHelper;
import uw.gateway.center.acl.filter.vo.MscAclFilterResult;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.constant.GatewayCenterResponseCode;
import uw.gateway.center.dto.MscAclFilterQueryParam;
import uw.gateway.center.entity.MscAclFilter;
import uw.gateway.center.entity.MscAclFilterData;
import uw.gateway.center.vo.MscAclFilterEx;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonState;
import uw.common.app.dto.IdStateQueryParam;
import uw.common.app.dto.SysCritLogQueryParam;
import uw.common.app.entity.SysCritLog;
import uw.common.dto.ResponseData;
import uw.common.util.IpMatchUtils;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.dao.vo.QueryParamResult;

import java.util.Date;
import java.util.stream.Collectors;


/**
 * IP访问控制。
 */
@RestController
@RequestMapping("/ops/acl/filter")
@Tag(name = "IP访问控制", description = "IP过滤器增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class MscAclFilterController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表IP过滤器。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "列表IP过滤器", description = "列表IP过滤器")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAclFilterEx>> list(MscAclFilterQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAclFilter.class);
        //附加子表过滤条件。
        if (queryParam.getDataQueryParam() != null) {
            QueryParamResult queryParamResult = dao.parseQueryParam(queryParam.getDataQueryParam());
            if (queryParamResult != null) {
                queryParam.ADD_EXT_COND("id in (select filter_id from msc_acl_filter_data " + queryParamResult.getSql() + ")", queryParamResult.getParamList());
            }
        }
        return dao.list(MscAclFilterEx.class, queryParam).onSuccess(filterList -> {
            if (filterList.size() > 0) {
                String filterIds = filterList.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.joining(","));
                DataList<MscAclFilterData> dataList = dao.list(MscAclFilterData.class, "SELECT * from msc_acl_filter_data where filter_id in (" + filterIds + ")").getData();
                filterList.results().forEach(x -> x.setDataList(dataList.stream().filter(y -> y.getFilterId() == x.getId()).collect(Collectors.toList())));
            }
        });
    }

    /**
     * 轻量级列表IP过滤器，一般用于select控件。
     *
     * @return
     */
    @GetMapping("/liteList")
    @Operation(summary = "轻量级列表IP过滤器", description = "轻量级列表IP过滤器，一般用于select控件。")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<DataList<MscAclFilter>> liteList(MscAclFilterQueryParam queryParam) {
        queryParam.SELECT_SQL("SELECT id,saas_id,user_id,user_type,filter_type,filter_name,filter_desc,create_date,modify_date,state from msc_acl_filter ");
        return dao.list(MscAclFilter.class, queryParam);
    }

    /**
     * 加载IP过滤器。
     *
     * @param id
     */
    @GetMapping("/load")
    @Operation(summary = "加载IP过滤器", description = "加载IP过滤器")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAclFilterEx> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAclFilter.class, id);
        return dao.load(MscAclFilterEx.class, id).onSuccess(filter -> {
            filter.setDataList(dao.list(MscAclFilterData.class, "SELECT * from msc_acl_filter_data where filter_id=?", new Object[]{id}).getData().results());
        });
    }

    /**
     * 查询操作日志。
     *
     * @param
     * @return
     */
    @GetMapping("/listCritLog")
    @Operation(summary = "查询操作日志", description = "查询操作日志")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<SysCritLog>> listCritLog(SysCritLogQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAclFilter.class, queryParam.getBizId());
        queryParam.setBizTypeClass(MscAclFilter.class);
        return dao.list(SysCritLog.class, queryParam);
    }

    /**
     * 测试IP过滤。
     *
     * @param
     * @return
     */
    @PostMapping("/testFilterIp")
    @Operation(summary = "测试IP过滤", description = "测试IP过滤")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<MscAclFilterResult> testFilterIp(@Parameter(description = "saasId") @RequestParam long saasId, @Parameter(description = "用户类型") @RequestParam int userType, @Parameter(description = "用户Id") @RequestParam long userId, @Parameter(description = "访问IP") @RequestParam String ipPermit) {
        return ResponseData.success(MscAclHelper.testFilterIp(saasId, userType, userId, IpMatchUtils.parseInetAddress(ipPermit)));
    }

    /**
     * 新增IP过滤器。
     *
     * @param mscAclFilter
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增IP过滤器", description = "新增IP过滤器")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAclFilter> save(@RequestBody MscAclFilter mscAclFilter) {
        MscAclFilter exists = dao.queryForSingleObject(MscAclFilter.class, "select * from msc_acl_filter where saas_id=? and user_type=? and user_id=? and state>?", new Object[]{mscAclFilter.getSaasId(), mscAclFilter.getUserType(), mscAclFilter.getUserId(), CommonState.DELETED.getValue()}).getData();
        if (exists != null) {
            return ResponseData.errorCode(GatewayCenterResponseCode.MSC_ACL_EXISTS_ERROR, exists.getId(), exists.getFilterName());
        }
        long id = dao.getSequenceId(MscAclFilter.class);
        AuthServiceHelper.logRef(MscAclFilter.class, id);
        mscAclFilter.setId(id);
        mscAclFilter.setCreateDate(new Date());
        mscAclFilter.setModifyDate(null);
        mscAclFilter.setState(CommonState.ENABLED.getValue());
        //设置申请人资料
        mscAclFilter.setApplyUserId(AuthServiceHelper.getUserId());
        mscAclFilter.setApplyUserInfo(AuthServiceHelper.getUserName());
        mscAclFilter.setApplyUserIp(AuthServiceHelper.getRemoteIp());
        mscAclFilter.setApplyDate(new Date());
        //初始化审批人资料
        mscAclFilter.setAuditUserId(0);
        mscAclFilter.setAuditUserInfo(null);
        mscAclFilter.setAuditUserIp(null);
        mscAclFilter.setAuditRemark(null);
        mscAclFilter.setAuditDate(null);
        mscAclFilter.setAuditState(AclAuditState.INIT.getValue());
        //保存信息
        return dao.save(mscAclFilter);
    }

    /**
     * 启用IP过滤器。
     *
     * @param id
     */
    @PutMapping("/enable")
    @Operation(summary = "启用IP过滤器", description = "启用IP过滤器")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilter.class, id, remark);
        MscAclFilter mscAclFilter = new MscAclFilter();
        mscAclFilter.setModifyDate(new Date());
        mscAclFilter.setState(CommonState.ENABLED.getValue());
        return dao.update(mscAclFilter, new IdStateQueryParam(id, CommonState.DISABLED.getValue())).onSuccess(updateResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclFilterCache(mscAclFilter.getSaasId());
        });
    }

    /**
     * 禁用IP过滤器。
     *
     * @param id
     */
    @PutMapping("/disable")
    @Operation(summary = "禁用IP过滤器", description = "禁用IP过滤器")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilter.class, id, remark);
        MscAclFilter mscAclFilter = new MscAclFilter();
        mscAclFilter.setModifyDate(new Date());
        mscAclFilter.setState(CommonState.DISABLED.getValue());
        return dao.update(mscAclFilter, new IdStateQueryParam(id, CommonState.ENABLED.getValue())).onSuccess(updateResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclFilterCache(mscAclFilter.getSaasId());
        });
    }

    /**
     * 删除IP过滤器。
     *
     * @param id
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除IP过滤器", description = "删除IP过滤器")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData delete(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilter.class, id, remark);
        MscAclFilter mscAclFilter = new MscAclFilter();
        mscAclFilter.setModifyDate(new Date());
        mscAclFilter.setState(CommonState.DELETED.getValue());
        return dao.update(mscAclFilter, new IdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

    /**
     * 新增IP访问控制数据。
     *
     * @param mscAclFilterData
     * @return
     */
    @PostMapping("/saveData")
    @Operation(summary = "新增IP访问控制数据", description = "新增IP访问控制数据")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAclFilterData> saveData(@RequestBody MscAclFilterData mscAclFilterData) {
        long id = dao.getSequenceId(MscAclFilter.class);
        AuthServiceHelper.logRef(MscAclFilter.class, mscAclFilterData.getFilterId());
        try {
            IpMatchUtils.IpRange ipRange = new IpMatchUtils.IpRange(mscAclFilterData.getIpInfo());
            mscAclFilterData.setIpStart(ipRange.getStartAddress().getHostAddress());
            mscAclFilterData.setIpEnd(ipRange.getEndAddress().getHostAddress());
        } catch (Exception e) {
            return ResponseData.errorCode(GatewayCenterResponseCode.MSC_ACL_IP_DATA_ERROR);
        }
        mscAclFilterData.setId(id);
        mscAclFilterData.setCreateDate(new Date());
        mscAclFilterData.setModifyDate(null);
        mscAclFilterData.setState(CommonState.ENABLED.getValue());
        //设置申请人资料
        mscAclFilterData.setApplyUserId(AuthServiceHelper.getUserId());
        mscAclFilterData.setApplyUserInfo(AuthServiceHelper.getUserName());
        mscAclFilterData.setApplyUserIp(AuthServiceHelper.getRemoteIp());
        mscAclFilterData.setApplyDate(new Date());
        //初始化审批人资料
        mscAclFilterData.setAuditUserId(0);
        mscAclFilterData.setAuditUserInfo(null);
        mscAclFilterData.setAuditUserIp(null);
        mscAclFilterData.setAuditRemark(null);
        mscAclFilterData.setAuditDate(null);
        mscAclFilterData.setAuditState(AclAuditState.INIT.getValue());
        return dao.save(mscAclFilterData).onSuccess(saveResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclFilterCache(mscAclFilterData.getSaasId());
        });
    }

    /**
     * 启用IP访问控制数据。
     *
     * @param id
     */
    @PutMapping("/enableData")
    @Operation(summary = "启用IP访问控制数据", description = "启用IP访问控制数据")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enableData(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilterData.class, id, remark);
        MscAclFilterData mscAclFilterData = new MscAclFilterData();
        mscAclFilterData.setModifyDate(new Date());
        mscAclFilterData.setState(CommonState.ENABLED.getValue());
        return dao.update(mscAclFilterData, new IdStateQueryParam(id, CommonState.DISABLED.getValue())).onSuccess(updateResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclFilterCache(mscAclFilterData.getSaasId());
        });
    }

    /**
     * 禁用IP访问控制数据。
     *
     * @param id
     */
    @PutMapping("/disableData")
    @Operation(summary = "禁用IP访问控制数据", description = "禁用IP访问控制数据")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disableData(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilterData.class, id, remark);
        MscAclFilterData mscAclFilterData = new MscAclFilterData();
        mscAclFilterData.setModifyDate(new Date());
        mscAclFilterData.setState(CommonState.DISABLED.getValue());
        return dao.update(mscAclFilterData, new IdStateQueryParam(id, CommonState.ENABLED.getValue())).onSuccess(updateResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclFilterCache(mscAclFilterData.getSaasId());
        });
    }

    /**
     * 删除IP访问控制数据。
     *
     * @param id
     */
    @DeleteMapping("/deleteData")
    @Operation(summary = "删除IP访问控制数据", description = "删除IP访问控制数据")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData deleteData(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilterData.class, id, remark);
        MscAclFilterData mscAclFilterData = new MscAclFilterData();
        mscAclFilterData.setModifyDate(new Date());
        mscAclFilterData.setState(CommonState.DELETED.getValue());
        return dao.update(mscAclFilterData, new IdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }
}