package uw.gateway.center.controller.root.acl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.gateway.center.acl.MscAclHelper;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.dto.MscAclRateQueryParam;
import uw.gateway.center.entity.MscAclRate;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonResponseCode;
import uw.common.dto.ResponseData;
import uw.dao.DaoManager;
import uw.dao.DataList;

import java.util.Date;


/**
 * 系统流控配置管理。
 */
@RestController
@RequestMapping("/root/acl/rateAudit")
@Tag(name = "系统流控配置审批", description = "系统流控配置审批")
@MscPermDeclare(user = UserType.ROOT)
public class MscAclRateAuditController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表系统流控配置。
     *
     * @param queryParam
     * @return
     * 
     */
    @GetMapping("/list")
    @Operation(summary = "列表系统流控配置", description = "列表系统流控配置")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAclRate>> list(MscAclRateQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAclRate.class);
        return dao.list(MscAclRate.class, queryParam);
    }

    /**
     * 加载系统流控配置。
     *
     * @param id
     * 
     */
    @GetMapping("/load")
    @Operation(summary = "加载系统流控配置", description = "加载系统流控配置")
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAclRate> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAclRate.class, id);
        return dao.load(MscAclRate.class, id);
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
        AuthServiceHelper.logInfo(MscAclRate.class, id, remark);
        var mscAclRateResponse = dao.load(MscAclRate.class, id  );
        if (mscAclRateResponse.isNotSuccess()) {
            return mscAclRateResponse;
        }
        var mscAclRate = mscAclRateResponse.getData();
        if (mscAclRateResponse.getData().getAuditState()!= AclAuditState.INIT.getValue()){
            return ResponseData.errorCode( CommonResponseCode.ENTITY_STATE_ERROR );
        }
        mscAclRate.setAuditState( AclAuditState.CONFIRM.getValue());
        mscAclRate.setAuditUserId( AuthServiceHelper.getUserId() );
        mscAclRate.setAuditUserInfo( AuthServiceHelper.getUserName() );
        mscAclRate.setAuditUserIp( AuthServiceHelper.getRemoteIp() );
        mscAclRate.setAuditRemark( remark );
        mscAclRate.setAuditDate( new Date() );
        return dao.update(mscAclRate).onSuccess(data -> {
            //更新缓存
            MscAclHelper.invalidateAclRateCache(mscAclRate.getSaasId());
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
        AuthServiceHelper.logInfo(MscAclRate.class, id, remark);
        var mscAclRateResponse = dao.load(MscAclRate.class, id  );
        if (mscAclRateResponse.isNotSuccess()) {
            return mscAclRateResponse;
        }
        var mscAclRate = mscAclRateResponse.getData();
        if (mscAclRateResponse.getData().getAuditState()!= AclAuditState.INIT.getValue()){
            return ResponseData.errorCode( CommonResponseCode.ENTITY_STATE_ERROR );
        }
        mscAclRate.setAuditState( AclAuditState.REJECT.getValue());
        mscAclRate.setAuditUserId( AuthServiceHelper.getUserId() );
        mscAclRate.setAuditUserInfo( AuthServiceHelper.getUserName() );
        mscAclRate.setAuditUserIp( AuthServiceHelper.getRemoteIp() );
        mscAclRate.setAuditRemark( remark );
        mscAclRate.setAuditDate( new Date() );
        return dao.update(mscAclRate);
    }

}