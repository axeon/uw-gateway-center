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
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.gateway.center.acme.AcmeHelper;
import uw.gateway.center.dto.MscAcmeDomainQueryParam;
import uw.gateway.center.entity.MscAcmeDomain;


/**
 * ACME域名管理。
 */
@RestController
@RequestMapping("/ops/acme/domain")
@Tag(name = "ACME域名管理", description = "acme域名增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class MscAcmeDomainController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表acme域名。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "列表acme域名", description = "列表acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAcmeDomain>> list(MscAcmeDomainQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAcmeDomain.class);
        return dao.list(MscAcmeDomain.class, queryParam);
    }

    /**
     * 轻量级列表acme域名，一般用于select控件。
     *
     * @return
     */
    @GetMapping("/liteList")
    @Operation(summary = "轻量级列表acme域名", description = "轻量级列表acme域名，一般用于select控件。")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<DataList<MscAcmeDomain>> liteList(MscAcmeDomainQueryParam queryParam) {
        queryParam.SELECT_SQL( "SELECT id,saas_id,account_id,domain_name,domain_cert_alg,acme_vendor,dns_vendor,last_update,last_active_date,last_expire_date,create_date,modify_date,state from msc_acme_domain " );
        return dao.list(MscAcmeDomain.class, queryParam);
    }

    /**
     * 加载acme域名。
     *
     * @param id
     */
    @GetMapping("/load")
    @Operation(summary = "加载acme域名", description = "加载acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAcmeDomain> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAcmeDomain.class, id);
        return dao.queryForSingleObject(MscAcmeDomain.class, new AuthIdQueryParam(id));
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
        AuthServiceHelper.logRef(MscAcmeDomain.class, queryParam.getEntityId());
        queryParam.setEntityClass(MscAcmeDomain.class);
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
        AuthServiceHelper.logRef(MscAcmeDomain.class, queryParam.getBizId());
        queryParam.setBizTypeClass(MscAcmeDomain.class);
        return dao.list(SysCritLog.class, queryParam);
    }

    /**
     * 申请证书。
     *
     * @return
     */
    @PostMapping("/applyCert")
    @Operation(summary = "申请证书", description = "申请证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<?> applyCert(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDomain.class, id, remark);
        return dao.queryForSingleObject(MscAcmeDomain.class, new AuthIdQueryParam(id)).onSuccess(mscAcmeDomainDb -> {
            //尝试加锁，先锁定60S.
            long lockStamp = GlobalLocker.tryLock("AcmeDomainApplyCert", id, 180_000L);
            if (lockStamp == 0) {
                return ResponseData.errorMsg("申请证书任务正在执行中。。。请稍后申请任务完成！");
            } else {
                return AcmeHelper.asyncApplyCert(id);
            }
        });

    }

    /**
     * 新增acme域名。
     *
     * @param mscAcmeDomain
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增acme域名", description = "新增acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeDomain> save(@RequestBody MscAcmeDomain mscAcmeDomain) {
        long id = dao.getSequenceId(MscAcmeDomain.class);
        AuthServiceHelper.logRef(MscAcmeDomain.class, id);
        mscAcmeDomain.setId(id);
        mscAcmeDomain.setSaasId(AuthServiceHelper.getSaasId());
        mscAcmeDomain.setCreateDate(SystemClock.nowDate());
        mscAcmeDomain.setModifyDate(null);
        mscAcmeDomain.setState(CommonState.ENABLED.getValue());
        return dao.save(mscAcmeDomain).onSuccess(savedEntity -> {
            //保存历史记录
            SysDataHistoryHelper.saveHistory(mscAcmeDomain);
        });
    }

    /**
     * 修改acme域名。
     *
     * @param mscAcmeDomain
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "修改acme域名", description = "修改acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeDomain> update(@RequestBody MscAcmeDomain mscAcmeDomain, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDomain.class, mscAcmeDomain.getId(), remark);
        return dao.queryForSingleObject(MscAcmeDomain.class, new AuthIdQueryParam(mscAcmeDomain.getId())).onSuccess(mscAcmeDomainDb -> {
            mscAcmeDomainDb.setDomainName(mscAcmeDomain.getDomainName());
            mscAcmeDomainDb.setDomainAlias(mscAcmeDomain.getDomainAlias());
            mscAcmeDomainDb.setAccountId(mscAcmeDomain.getAccountId());
            mscAcmeDomainDb.setDomainDesc(mscAcmeDomain.getDomainDesc());
            mscAcmeDomainDb.setAcmeVendor(mscAcmeDomain.getAcmeVendor());
            mscAcmeDomainDb.setDnsVendor(mscAcmeDomain.getDnsVendor());
            mscAcmeDomainDb.setDnsParam(mscAcmeDomain.getDnsParam());
            mscAcmeDomainDb.setModifyDate(SystemClock.nowDate());
            return dao.update(mscAcmeDomainDb).onSuccess(updatedEntity -> {
                //保存历史记录
                SysDataHistoryHelper.saveHistory(mscAcmeDomainDb, remark);
            });
        });
    }

    /**
     * 启用acme域名。
     *
     * @param id
     */
    @PutMapping("/enable")
    @Operation(summary = "启用acme域名", description = "启用acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDomain.class, id, remark);
        return dao.update(new MscAcmeDomain().modifyDate(SystemClock.nowDate()).state(CommonState.ENABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

    /**
     * 禁用acme域名。
     *
     * @param id
     */
    @PutMapping("/disable")
    @Operation(summary = "禁用acme域名", description = "禁用acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDomain.class, id, remark);
        return dao.update(new MscAcmeDomain().modifyDate(SystemClock.nowDate()).state(CommonState.DISABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.ENABLED.getValue()));
    }

    /**
     * 删除acme域名。
     *
     * @param id
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除acme域名", description = "删除acme域名")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData delete(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeDomain.class, id, remark);
        return dao.update(new MscAcmeDomain().modifyDate(SystemClock.nowDate()).state(CommonState.DELETED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

}