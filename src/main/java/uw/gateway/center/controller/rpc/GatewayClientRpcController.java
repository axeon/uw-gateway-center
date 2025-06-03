package uw.gateway.center.controller.rpc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonState;
import uw.common.dto.ResponseData;
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.gateway.center.acl.MscAclHelper;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.constant.RateLimitType;
import uw.gateway.center.entity.MscAclRate;

import java.util.Date;

public class GatewayClientRpcController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayClientRpcController.class);

    /**
     * Dao工厂。
     */
    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 运营商限速设置
     *
     * @param saasId
     * @param remark
     * @return
     */
    @PutMapping("/updateSaasRateLimit")
    @Operation(summary = "更新运营商限速设置", description = "更新运营商限速设置")
    @MscPermDeclare(user = UserType.RPC, log = ActionLog.CRIT)
    public ResponseData updateSaasRateLimit(@Parameter(description = "saasId") @RequestParam long saasId, @Parameter(description = "限制秒数", example = "10") @RequestParam int limitSeconds, @Parameter(description = "限制请求书", example = "1000") @RequestParam int limitRequests, @Parameter(description = "限制字节数", example = "500000") @RequestParam int limitBytes, @Parameter(description = "过期时间") @RequestParam Date expireDate, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclRate.class, 0, "更新运营商[" + saasId + "]限速设置！操作备注：" + remark);
        MscAclRate mscAclRate = new MscAclRate();
        long id = dao.getSequenceId(MscAclRate.class);
        mscAclRate.setId(id);
        mscAclRate.setSaasId(saasId);
        mscAclRate.setUserType(-1);
        mscAclRate.setUserId(-1L);
        mscAclRate.setLimitType(RateLimitType.SAAS.getValue());
        mscAclRate.setLimitUri(null);
        mscAclRate.setLimitSeconds(limitSeconds);
        mscAclRate.setLimitRequests(limitRequests);
        mscAclRate.setLimitBytes(limitBytes);
        mscAclRate.setExpireDate(expireDate);
        mscAclRate.setCreateDate(SystemClock.nowDate());
        mscAclRate.setModifyDate(null);
        mscAclRate.setState(CommonState.ENABLED.getValue());
        //设置申请人资料
        mscAclRate.setApplyUserId(AuthServiceHelper.getUserId());
        mscAclRate.setApplyUserInfo(AuthServiceHelper.getUserName());
        mscAclRate.setApplyUserIp(AuthServiceHelper.getRemoteIp());
        mscAclRate.setApplyDate(SystemClock.nowDate());
        mscAclRate.setApplyRemark("RPC更新配置。");
        //初始化审批人资料
        mscAclRate.setAuditUserId(AuthServiceHelper.getUserId());
        mscAclRate.setAuditUserInfo(AuthServiceHelper.getUserName());
        mscAclRate.setAuditUserIp(AuthServiceHelper.getRemoteIp());
        mscAclRate.setAuditRemark("RPC自动审批通过。");
        mscAclRate.setAuditDate(SystemClock.nowDate());
        mscAclRate.setAuditState(AclAuditState.CONFIRM.getValue());
        dao.save(mscAclRate);
        //更新缓存
        MscAclHelper.invalidateAclRateCache(saasId);
        return ResponseData.success();
    }

    /**
     * 清除运营商限速设置。
     *
     * @param saasId
     * @param remark
     * @return
     */
    @PutMapping("/clearSaasRateLimit")
    @Operation(summary = "清除运营商限速设置", description = "清除运营商限速设置")
    @MscPermDeclare(user = UserType.RPC, log = ActionLog.CRIT)
    public ResponseData clearSaasRateLimit(@Parameter(description = "saasId") @RequestParam long saasId, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclRate.class, 0, "清除运营商[" + saasId + "]限速设置！操作备注：" + remark);
        dao.executeCommand("update msc_acl_rate set modify_date=?, state=? where saas_id=? and state>? ", new Object[]{SystemClock.nowDate(), CommonState.DELETED.getValue(), saasId, CommonState.ENABLED.getValue()});
        //更新缓存
        MscAclHelper.invalidateAclRateCache(saasId);
        return ResponseData.success();
    }

}
