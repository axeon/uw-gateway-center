package uw.gateway.center.controller.ops.acme;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.cache.GlobalLocker;
import uw.common.app.constant.CommonState;
import uw.common.app.dto.AuthIdQueryParam;
import uw.common.app.dto.AuthIdStateQueryParam;
import uw.common.app.dto.SysCritLogQueryParam;
import uw.common.app.dto.SysDataHistoryQueryParam;
import uw.common.app.entity.SysCritLog;
import uw.common.app.entity.SysDataHistory;
import uw.common.app.helper.SysDataHistoryHelper;
import uw.common.dto.ResponseData;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.gateway.center.acme.AcmeHelper;
import uw.gateway.center.dto.MscAcmeDeployLogQueryParam;
import uw.gateway.center.dto.MscAcmeDeployQueryParam;
import uw.gateway.center.entity.MscAcmeDeploy;
import uw.gateway.center.entity.MscAcmeDeployLog;

import java.util.Date;


/**
 * ACME部署管理。
 */
@RestController
@RequestMapping("/ops/acme/domain/deploy")
@Tag(name = "ACME部署管理", description = "acme部署增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class MscAcmeDeployController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表acme部署。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "列表acme部署", description = "列表acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAcmeDeploy>> list(MscAcmeDeployQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAcmeDeploy.class);
        return dao.list(MscAcmeDeploy.class, queryParam);
    }

    /**
     * 轻量级列表acme部署，一般用于select控件。
     *
     * @return
     */
    @GetMapping("/liteList")
    @Operation(summary = "轻量级列表acme部署", description = "轻量级列表acme部署，一般用于select控件。")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<DataList<MscAcmeDeploy>> liteList(MscAcmeDeployQueryParam queryParam) {
        queryParam.SELECT_SQL( "SELECT id,saas_id,domain_id,deploy_name,deploy_vendor,last_update,last_active_date,last_expire_date,create_date,modify_date,state from msc_acme_deploy " );
        return dao.list(MscAcmeDeploy.class, queryParam);
    }

    /**
     * 加载acme部署。
     *
     * @param id
     */
    @GetMapping("/load")
    @Operation(summary = "加载acme部署", description = "加载acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAcmeDeploy> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAcmeDeploy.class, id);
        return dao.queryForSingleObject(MscAcmeDeploy.class, new AuthIdQueryParam(id));
    }

    /**
     * 列表acme部署日志。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/listLog")
    @Operation(summary = "列表acme部署日志", description = "列表acme部署日志")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAcmeDeployLog>> listLog(MscAcmeDeployLogQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAcmeDeployLog.class);
        return dao.list(MscAcmeDeployLog.class, queryParam);
    }

    /**
     * 查询数据历史。
     *
     * @param
     * @return
     */
    @GetMapping("/listDataHistory")
    @Operation(summary = "查询数据历史", description = "查询数据历史")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<SysDataHistory>> listDataHistory(SysDataHistoryQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAcmeDeploy.class, queryParam.getEntityId());
        queryParam.setEntityClass(MscAcmeDeploy.class);
        return dao.list(SysDataHistory.class, queryParam);
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
        AuthServiceHelper.logRef(MscAcmeDeploy.class, queryParam.getBizId());
        queryParam.setBizTypeClass(MscAcmeDeploy.class);
        return dao.list(SysCritLog.class, queryParam);
    }

    /**
     * 部署证书。
     *
     * @return
     */
    @PostMapping("/deployCert")
    @Operation(summary = "部署证书", description = "部署证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<?> applyCert(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "证书ID") @RequestParam long certId, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDeploy.class, id, remark);
        return dao.queryForSingleObject(MscAcmeDeploy.class, new AuthIdQueryParam(id)).onSuccess(mscAcmeDeployDb -> {
            //尝试加锁，先锁定180S.
            long lockStamp = GlobalLocker.tryLock("AcmeDeployCert", id, 180_000L);
            if (lockStamp == 0) {
                return ResponseData.errorMsg("部署证书任务正在执行中。。。请稍后部署任务完成！");
            } else {
                return AcmeHelper.asyncDeployCert(id, certId);
            }
        });

    }

    /**
     * 新增acme部署。
     *
     * @param mscAcmeDeploy
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增acme部署", description = "新增acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeDeploy> save(@RequestBody MscAcmeDeploy mscAcmeDeploy) {
        long id = dao.getSequenceId(MscAcmeDeploy.class);
        AuthServiceHelper.logRef(MscAcmeDeploy.class, id);
        mscAcmeDeploy.setId(id);
        mscAcmeDeploy.setSaasId(AuthServiceHelper.getSaasId());
        mscAcmeDeploy.setCreateDate(new Date());
        mscAcmeDeploy.setModifyDate(null);
        mscAcmeDeploy.setState(CommonState.ENABLED.getValue());
        return dao.save(mscAcmeDeploy).onSuccess(savedEntity -> {
            //保存历史记录
            SysDataHistoryHelper.saveHistory(mscAcmeDeploy);
        });
    }

    /**
     * 修改acme部署。
     *
     * @param mscAcmeDeploy
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "修改acme部署", description = "修改acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeDeploy> update(@RequestBody MscAcmeDeploy mscAcmeDeploy, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDeploy.class, mscAcmeDeploy.getId(), remark);
        return dao.queryForSingleObject(MscAcmeDeploy.class, new AuthIdQueryParam(mscAcmeDeploy.getId())).onSuccess(mscAcmeDeployDb -> {
            mscAcmeDeployDb.setDomainId(mscAcmeDeploy.getDomainId());
            mscAcmeDeployDb.setDeployName(mscAcmeDeploy.getDeployName());
            mscAcmeDeployDb.setDeployDesc(mscAcmeDeploy.getDeployDesc());
            mscAcmeDeployDb.setDeployVendor(mscAcmeDeploy.getDeployVendor());
            mscAcmeDeployDb.setDeployParam(mscAcmeDeploy.getDeployParam());
            mscAcmeDeployDb.setLastUpdate(mscAcmeDeploy.getLastUpdate());
            mscAcmeDeployDb.setModifyDate(new Date());
            return dao.update(mscAcmeDeployDb).onSuccess(updatedEntity -> {
                //保存历史记录
                SysDataHistoryHelper.saveHistory(mscAcmeDeployDb, remark);
            });
        });
    }

    /**
     * 启用acme部署。
     *
     * @param id
     */
    @PutMapping("/enable")
    @Operation(summary = "启用acme部署", description = "启用acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDeploy.class, id, remark);
        return dao.update(new MscAcmeDeploy().modifyDate(new Date()).state(CommonState.ENABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

    /**
     * 禁用acme部署。
     *
     * @param id
     */
    @PutMapping("/disable")
    @Operation(summary = "禁用acme部署", description = "禁用acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDeploy.class, id, remark);
        return dao.update(new MscAcmeDeploy().modifyDate(new Date()).state(CommonState.DISABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.ENABLED.getValue()));
    }

    /**
     * 删除acme部署。
     *
     * @param id
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除acme部署", description = "删除acme部署")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData delete(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDeploy.class, id, remark);
        return dao.update(new MscAcmeDeploy().modifyDate(new Date()).state(CommonState.DELETED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

}