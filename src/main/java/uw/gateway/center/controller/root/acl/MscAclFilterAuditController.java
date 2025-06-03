package uw.gateway.center.controller.root.acl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonResponseCode;
import uw.common.dto.ResponseData;
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.dao.vo.QueryParamResult;
import uw.gateway.center.acl.MscAclHelper;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.dto.MscAclFilterQueryParam;
import uw.gateway.center.entity.MscAclFilter;
import uw.gateway.center.entity.MscAclFilterData;
import uw.gateway.center.vo.MscAclFilterEx;

import java.util.stream.Collectors;


/**
 * IP访问控制。
 */
@RestController
@RequestMapping("/root/acl/filterAudit")
@Tag(name = "IP访问控制审批", description = "IP访问控制审批")
@MscPermDeclare(user = UserType.ROOT)
public class MscAclFilterAuditController {

    private final DaoManager dao = DaoManager.getInstance();


    /**
     * 列表IP过滤器。
     *
     * @param queryParam
     * @return
     * 
     */
    @GetMapping("/list")
    @Operation(summary = "列表IP过滤器", description = "列表IP过滤器")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.REQUEST)
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
     * 加载IP过滤器。
     *
     * @param id
     * 
     */
    @GetMapping("/load")
    @Operation(summary = "加载IP过滤器", description = "加载IP过滤器")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAclFilterEx> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAclFilter.class, id);
        return dao.load(MscAclFilterEx.class, id).onSuccess(filter -> {
            filter.setDataList(dao.list(MscAclFilterData.class, "SELECT * from msc_acl_filter_data where filter_id=?", new Object[]{id}).getData().results());
        });
    }

    /**
     * 审批通过。
     *
     * @param id
     * @param remark 审批备注
     * @return
     * 
     */
    @PutMapping("/auditConfirm")
    @Operation(summary = "审批通过", description = "审批通过")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData auditConfirm(@RequestParam long id, @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilter.class, id, remark);
        return dao.load(MscAclFilter.class, id).onSuccess(mscAclFilter -> {
            if (mscAclFilter.getAuditState() != AclAuditState.INIT.getValue()) {
                return ResponseData.errorCode(CommonResponseCode.ENTITY_STATE_ERROR);
            }
            mscAclFilter.setAuditState(AclAuditState.CONFIRM.getValue());
            mscAclFilter.setAuditUserId(AuthServiceHelper.getUserId());
            mscAclFilter.setAuditUserInfo(AuthServiceHelper.getUserName());
            mscAclFilter.setAuditUserIp(AuthServiceHelper.getRemoteIp());
            mscAclFilter.setAuditRemark(remark);
            mscAclFilter.setAuditDate(SystemClock.nowDate());
            return dao.update(mscAclFilter).onSuccess(updateResponse -> {
                //更新缓存
                MscAclHelper.invalidateAclFilterCache(mscAclFilter.getSaasId());
            });
        });
    }

    /**
     * 审批拒绝。
     *
     * @param id
     * @param remark 审批备注
     * @return
     * 
     */
    @PutMapping("/auditReject")
    @Operation(summary = "审批拒绝", description = "审批拒绝")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData auditReject(@RequestParam long id, @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilter.class, id, remark);
        return dao.load(MscAclFilter.class, id).onSuccess(mscAclFilter -> {
            if (mscAclFilter.getAuditState() != AclAuditState.INIT.getValue()) {
                return ResponseData.errorCode(CommonResponseCode.ENTITY_STATE_ERROR);
            }
            mscAclFilter.setAuditState(AclAuditState.REJECT.getValue());
            mscAclFilter.setAuditUserId(AuthServiceHelper.getUserId());
            mscAclFilter.setAuditUserInfo(AuthServiceHelper.getUserName());
            mscAclFilter.setAuditUserIp(AuthServiceHelper.getRemoteIp());
            mscAclFilter.setAuditRemark(remark);
            mscAclFilter.setAuditDate(SystemClock.nowDate());
            return dao.update(mscAclFilter);
        });
    }


    /**
     * 审批通过数据。
     *
     * @param id
     * @param remark 审批备注
     * @return
     * 
     */
    @PutMapping("/auditConfirmData")
    @Operation(summary = "审批通过数据", description = "审批通过数据")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData auditConfirmData(@RequestParam long id, @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilterData.class, id, remark);
        return dao.load(MscAclFilterData.class, id).onSuccess(mscAclFilterData -> {
            if (mscAclFilterData.getAuditState() != AclAuditState.INIT.getValue()) {
                return ResponseData.errorCode(CommonResponseCode.ENTITY_STATE_ERROR);
            }
            mscAclFilterData.setAuditState(AclAuditState.CONFIRM.getValue());
            mscAclFilterData.setAuditUserId(AuthServiceHelper.getUserId());
            mscAclFilterData.setAuditUserInfo(AuthServiceHelper.getUserName());
            mscAclFilterData.setAuditUserIp(AuthServiceHelper.getRemoteIp());
            mscAclFilterData.setAuditRemark(remark);
            mscAclFilterData.setAuditDate(SystemClock.nowDate());
            return dao.update(mscAclFilterData).onSuccess(updateResponse -> {
                MscAclHelper.invalidateAclFilterCache(mscAclFilterData.getSaasId());
            });

        });
    }

    /**
     * 审批拒绝数据。
     *
     * @param id
     * @param remark 审批备注
     * @return
     * 
     */
    @PutMapping("/auditRejectData")
    @Operation(summary = "审批拒绝数据", description = "审批拒绝数据")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData auditRejectData(@RequestParam long id, @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclFilterData.class, id, remark);
        return dao.load(MscAclFilterData.class, id).onSuccess(mscAclFilterData -> {
            if (mscAclFilterData.getAuditState() != AclAuditState.INIT.getValue()) {
                return ResponseData.errorCode(CommonResponseCode.ENTITY_STATE_ERROR);
            }
            mscAclFilterData.setAuditState(AclAuditState.REJECT.getValue());
            mscAclFilterData.setAuditUserId(AuthServiceHelper.getUserId());
            mscAclFilterData.setAuditUserInfo(AuthServiceHelper.getUserName());
            mscAclFilterData.setAuditUserIp(AuthServiceHelper.getRemoteIp());
            mscAclFilterData.setAuditRemark(remark);
            mscAclFilterData.setAuditDate(SystemClock.nowDate());
            return dao.update(mscAclFilterData).onSuccess(updateResponse -> {
                MscAclHelper.invalidateAclFilterCache(mscAclFilterData.getSaasId());
            });

        });

    }

}