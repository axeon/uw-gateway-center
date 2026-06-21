package uw.gateway.center.controller.rpc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonState;
import uw.common.response.ResponseData;
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.gateway.center.acl.MscAclHelper;
import uw.gateway.center.acl.rate.constant.MscAclRateLimitType;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.entity.MscAclRate;
import uw.gateway.center.vo.SaasRateLimitParam;

/**
 * 网关客户端RPC接口。
 */
@RestController
@Tag(name = "网关客户端接口")
@RequestMapping("/rpc/service")
public class ServiceRpcController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRpcController.class);

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
    /**
     * 更新运营商限速设置。
     *
     * @param param 限速策略参数（限速维度由 limitType 决定，userType/userId 仅按用户维度限速时使用）
     * @return 操作结果
     */
    @PutMapping("/updateSaasRateLimit")
    @Operation(summary = "更新运营商限速设置", description = "更新运营商限速设置")
    @MscPermDeclare(user = UserType.RPC, log = ActionLog.CRIT)
    public ResponseData updateSaasRateLimit(@RequestBody SaasRateLimitParam param) {
        // ===== 参数全面校验，任一不合法直接返回，不记审计日志、不落库 =====
        if (param == null) {
            return ResponseData.errorMsg("param不能为空！");
        }
        if (param.getSaasId() <= 0) {
            return ResponseData.errorMsg("saasId必须大于0！");
        }
        if (!MscAclRateLimitType.isValidValue(param.getLimitType())) {
            return ResponseData.errorMsg("limitType非法！合法值: -1不限速/0 IP/1 SAAS_LEVEL/2 USER_TYPE/3 USER_ID/11 SAAS_LEVEL_URI/12 USER_TYPE_URI/13 USER_ID_URI");
        }
        // 按用户维度限速时，userType/userId 必须提供；非用户维度时强制归 -1，避免脏数据干扰匹配
        if (MscAclRateLimitType.findByValue(param.getLimitType()).isUserDimension()) {
            if (param.getUserType() < 0) {
                return ResponseData.errorMsg("按用户维度限速时userType必须大于等于0！");
            }
            if (param.getUserId() <= 0) {
                return ResponseData.errorMsg("按用户维度限速时userId必须大于0！");
            }
        } else {
            param.setUserType(-1);
            param.setUserId(-1L);
        }
        // NONE(-1) 表示不限速，此时窗口/配额无意义；其余类型要求至少限定请求数或字节数
        if (param.getLimitType() != MscAclRateLimitType.NONE.getValue()) {
            if (param.getLimitSeconds() <= 0) {
                return ResponseData.errorMsg("limitSeconds必须大于0！");
            }
            if (param.getLimitRequests() <= 0 && param.getLimitBytes() <= 0) {
                return ResponseData.errorMsg("limitRequests与limitBytes至少有一个必须大于0！");
            }
        }
        if (param.getExpireDate() == null) {
            return ResponseData.errorMsg("expireDate不能为空！");
        }
        if (param.getExpireDate().before(SystemClock.nowDate())) {
            return ResponseData.errorMsg("expireDate必须晚于当前时间！");
        }
        if (param.getRemark() == null || param.getRemark().isBlank()) {
            return ResponseData.errorMsg("remark不能为空！");
        }
        AuthServiceHelper.logInfo(MscAclRate.class, 0, "更新运营商[" + param.getSaasId() + "]限速设置！操作备注：" + param.getRemark());
        MscAclRate mscAclRate = new MscAclRate();
        long id = dao.getSequenceId(MscAclRate.class);
        mscAclRate.setId(id);
        mscAclRate.setSaasId(param.getSaasId());
        mscAclRate.setUserType(param.getUserType());
        mscAclRate.setUserId(param.getUserId());
        mscAclRate.setLimitType(param.getLimitType());
        mscAclRate.setLimitUri(null);
        mscAclRate.setLimitSeconds(param.getLimitSeconds());
        mscAclRate.setLimitRequests(param.getLimitRequests());
        mscAclRate.setLimitBytes(param.getLimitBytes());
        mscAclRate.setExpireDate(param.getExpireDate());
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
        MscAclHelper.invalidateAclRateCache(param.getSaasId());
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
        dao.execute("update msc_acl_rate set modify_date=?, state=? where saas_id=? and state>? ", new Object[]{SystemClock.nowDate(), CommonState.DELETED.getValue(), saasId, CommonState.ENABLED.getValue()});
        //更新缓存
        MscAclHelper.invalidateAclRateCache(saasId);
        return ResponseData.success();
    }

}
