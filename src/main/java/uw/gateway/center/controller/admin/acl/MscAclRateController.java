package uw.gateway.center.controller.admin.acl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
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
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.gateway.center.acl.MscAclHelper;
import uw.gateway.center.acl.rate.vo.MscAclRateResult;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.constant.GatewayCenterResponseCode;
import uw.gateway.center.dto.MscAclRateQueryParam;
import uw.gateway.center.entity.MscAclRate;

import java.util.Date;


/**
 * 系统流控配置管理。
 */
@RestController
@RequestMapping("/admin/acl/rate")
@Tag(name = "系统流控配置", description = "系统流控配置")
@MscPermDeclare(user = UserType.ADMIN)
public class MscAclRateController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表系统流控配置。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "列表系统流控配置", description = "列表系统流控配置")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAclRate>> list(MscAclRateQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAclRate.class);
        return dao.list(MscAclRate.class, queryParam);
    }

    /**
     * 轻量级列表系统流控配置，一般用于select控件。
     *
     * @return
     */
    @GetMapping("/liteList")
    @Operation(summary = "轻量级列表系统流控配置", description = "轻量级列表系统流控配置，一般用于select控件。")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<DataList<MscAclRate>> liteList(MscAclRateQueryParam queryParam) {
        queryParam.SELECT_SQL("SELECT id,saas_id,user_id,limit_type,limit_uri,limit_seconds,limit_requests,limit_bytes,remark,create_date,modify_date,state from msc_acl_rate ");
        return dao.list(MscAclRate.class, queryParam);
    }

    /**
     * 加载系统流控配置。
     *
     * @param id
     */
    @GetMapping("/load")
    @Operation(summary = "加载系统流控配置", description = "加载系统流控配置")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAclRate> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAclRate.class, id);
        return dao.load(MscAclRate.class, id);
    }

    /**
     * 查询操作日志。
     *
     * @param
     * @return
     */
    @GetMapping("/listCritLog")
    @Operation(summary = "查询操作日志", description = "查询操作日志")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<SysCritLog>> listCritLog(SysCritLogQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAclRate.class, queryParam.getBizId());
        queryParam.setBizTypeClass(MscAclRate.class);
        return dao.list(SysCritLog.class, queryParam);
    }

    /**
     * 测试流控限制。
     *
     * @param
     * @return
     */
    @PostMapping("/testRateLimit")
    @Operation(summary = "测试流控限制", description = "测试流控限制")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<MscAclRateResult> testRateLimit(@Parameter(description = "saasId") @RequestParam long saasId, @Parameter(description = "用户类型") @RequestParam int userType,
                                                        @Parameter(description = "用户Id") @RequestParam long userId, @Parameter(description = "资源地址") @RequestParam String uri,
                                                        @Parameter(description = "IP许可") @RequestParam String ipPermit, @Parameter(description = "请求数许可") @RequestParam int requestsPermit,
                                                        @Parameter(description = "字节数许可") @RequestParam int bytesPermit) {
        return ResponseData.success(MscAclHelper.testRateLimit(saasId, userType, userId, uri, ipPermit, requestsPermit, bytesPermit));
    }

    /**
     * 新增系统流控配置。
     *
     * @param mscAclRate
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增系统流控配置", description = "新增系统流控配置")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAclRate> save(@RequestBody MscAclRate mscAclRate) {
        //额外处理limitUri空值问题，否则会导致数据库匹配不上
        if (mscAclRate.getLimitUri() == null) {
            mscAclRate.setLimitUri("");
        }
        //判断是否已存在同类型配置
        MscAclRate exists = dao.queryForSingleObject(MscAclRate.class, "select * from msc_acl_rate where saas_id=? and saas_level=? and user_type=? and user_id=? and limit_type=? and limit_uri=? and state>?",
                new Object[]{mscAclRate.getSaasId(), mscAclRate.getSaasLevel(), mscAclRate.getUserType(), mscAclRate.getUserId(), mscAclRate.getLimitType(), mscAclRate.getLimitUri(), CommonState.DELETED.getValue()}).getData();
        if (exists != null) {
            return ResponseData.errorCode(GatewayCenterResponseCode.MSC_ACL_EXISTS_ERROR, exists.getId(), exists.getLimitName());
        }
        long id = dao.getSequenceId(MscAclRate.class);
        AuthServiceHelper.logRef(MscAclRate.class, id);
        mscAclRate.setId(id);
        mscAclRate.setCreateDate(new Date());
        mscAclRate.setModifyDate(null);
        mscAclRate.setState(CommonState.ENABLED.getValue());
        //设置申请人资料
        mscAclRate.setApplyUserId(AuthServiceHelper.getUserId());
        mscAclRate.setApplyUserInfo(AuthServiceHelper.getUserName());
        mscAclRate.setApplyUserIp(AuthServiceHelper.getRemoteIp());
        mscAclRate.setApplyDate(new Date());
        //初始化审批人资料
        mscAclRate.setAuditUserId(0);
        mscAclRate.setAuditUserInfo(null);
        mscAclRate.setAuditUserIp(null);
        mscAclRate.setAuditRemark(null);
        mscAclRate.setAuditDate(null);
        mscAclRate.setAuditState(AclAuditState.INIT.getValue());
        //保存信息
        return dao.save(mscAclRate);
    }

    /**
     * 启用系统流控配置。
     *
     * @param id
     */
    @PutMapping("/enable")
    @Operation(summary = "启用系统流控配置", description = "启用系统流控配置")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclRate.class, id, remark);
        MscAclRate mscAclRate = new MscAclRate();
        mscAclRate.setModifyDate(new Date());
        mscAclRate.setState(CommonState.ENABLED.getValue());
        return dao.update(mscAclRate, new IdStateQueryParam(id, CommonState.DISABLED.getValue())).onSuccess(updateResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclRateCache(mscAclRate.getSaasId());
        });
    }

    /**
     * 禁用系统流控配置。
     *
     * @param id
     */
    @PutMapping("/disable")
    @Operation(summary = "禁用系统流控配置", description = "禁用系统流控配置")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclRate.class, id, remark);
        AuthServiceHelper.logInfo(MscAclRate.class, id, remark);
        MscAclRate mscAclRate = new MscAclRate();
        mscAclRate.setModifyDate(new Date());
        mscAclRate.setState(CommonState.DISABLED.getValue());
        return dao.update(mscAclRate, new IdStateQueryParam(id, CommonState.ENABLED.getValue())).onSuccess(updateResponse -> {
            //更新缓存
            MscAclHelper.invalidateAclRateCache(mscAclRate.getSaasId());
        });

    }

    /**
     * 删除系统流控配置。
     *
     * @param id
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除系统流控配置", description = "删除系统流控配置")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData delete(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAclRate.class, id, remark);
        MscAclRate mscAclRate = new MscAclRate();
        mscAclRate.setModifyDate(new Date());
        mscAclRate.setState(CommonState.DELETED.getValue());
        return dao.update(mscAclRate, new IdStateQueryParam(id, CommonState.DISABLED.getValue()));

    }

}