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
import uw.gateway.center.dto.MscAcmeAccountQueryParam;
import uw.gateway.center.entity.MscAcmeAccount;

import java.util.Date;


/**
 * ACME账号管理。
 */
@RestController
@RequestMapping("/ops/acme/account")
@Tag(name = "ACME账号管理", description = "acme账号增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class MscAcmeAccountController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表acme账号。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "列表acme账号", description = "列表acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAcmeAccount>> list(MscAcmeAccountQueryParam queryParam) {
        queryParam.SELECT_SQL("SELECT id,saas_id,account_name,account_desc,account_cert_alg,create_date,modify_date,state from msc_acme_account ");
        AuthServiceHelper.logRef(MscAcmeAccount.class);
        return dao.list(MscAcmeAccount.class, queryParam);
    }

    /**
     * 轻量级列表acme账号，一般用于select控件。
     *
     * @return
     */
    @GetMapping("/liteList")
    @Operation(summary = "轻量级列表acme账号", description = "轻量级列表acme账号，一般用于select控件。")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<DataList<MscAcmeAccount>> liteList(MscAcmeAccountQueryParam queryParam) {
        queryParam.SELECT_SQL("SELECT id,saas_id,account_name,create_date,modify_date,state from msc_acme_account ");
        return dao.list(MscAcmeAccount.class, queryParam);
    }

    /**
     * 加载acme账号。
     *
     * @param id
     */
    @GetMapping("/load")
    @Operation(summary = "加载acme账号", description = "加载acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAcmeAccount> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAcmeAccount.class, id);
        return dao.queryForSingleObject(MscAcmeAccount.class, new AuthIdQueryParam(id));
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
        AuthServiceHelper.logRef(MscAcmeAccount.class, queryParam.getEntityId());
        queryParam.setEntityClass(MscAcmeAccount.class);
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
        AuthServiceHelper.logRef(MscAcmeAccount.class, queryParam.getBizId());
        queryParam.setBizTypeClass(MscAcmeAccount.class);
        return dao.list(SysCritLog.class, queryParam);
    }

    /**
     * 新增acme账号。
     *
     * @param mscAcmeAccount
     * @return
     */
    @PostMapping("/save")
    @Operation(summary = "新增acme账号", description = "新增acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeAccount> save(@RequestBody MscAcmeAccount mscAcmeAccount) {
        long id = dao.getSequenceId(MscAcmeAccount.class);
        AuthServiceHelper.logRef(MscAcmeAccount.class, id);
        mscAcmeAccount.setId(id);
        mscAcmeAccount.setSaasId(AuthServiceHelper.getSaasId());
        mscAcmeAccount.setCreateDate(new Date());
        mscAcmeAccount.setModifyDate(null);
        mscAcmeAccount.setState(CommonState.ENABLED.getValue());
        return dao.save(mscAcmeAccount).onSuccess(savedEntity -> {
            //保存历史记录
            SysDataHistoryHelper.saveHistory(mscAcmeAccount);
        });
    }


    /**
     * 修改acme账号。
     *
     * @param mscAcmeAccount
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "修改acme账号", description = "修改acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeAccount> update(@RequestBody MscAcmeAccount mscAcmeAccount, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeAccount.class, mscAcmeAccount.getId(), remark);
        return dao.queryForSingleObject(MscAcmeAccount.class, new AuthIdQueryParam(mscAcmeAccount.getId())).onSuccess(mscAcmeAccountDb -> {
            mscAcmeAccountDb.setAccountName(mscAcmeAccount.getAccountName());
            mscAcmeAccountDb.setAccountDesc(mscAcmeAccount.getAccountDesc());
            mscAcmeAccountDb.setEabId(mscAcmeAccount.getEabId());
            mscAcmeAccountDb.setEabKey(mscAcmeAccount.getEabKey());
            mscAcmeAccountDb.setModifyDate(new Date());
            return dao.update(mscAcmeAccountDb).onSuccess(updatedEntity -> {
                //保存历史记录
                SysDataHistoryHelper.saveHistory(mscAcmeAccountDb, remark);
            });
        });
    }

    /**
     * 启用acme账号。
     *
     * @param id
     */
    @PutMapping("/enable")
    @Operation(summary = "启用acme账号", description = "启用acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeAccount.class, id, remark);
        return dao.update(new MscAcmeAccount().modifyDate(new Date()).state(CommonState.ENABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

    /**
     * 禁用acme账号。
     *
     * @param id
     */
    @PutMapping("/disable")
    @Operation(summary = "禁用acme账号", description = "禁用acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeAccount.class, id, remark);
        return dao.update(new MscAcmeAccount().modifyDate(new Date()).state(CommonState.DISABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.ENABLED.getValue()));
    }

    /**
     * 删除acme账号。
     *
     * @param id
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除acme账号", description = "删除acme账号")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData delete(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeAccount.class, id, remark);
        return dao.update(new MscAcmeAccount().modifyDate(new Date()).state(CommonState.DELETED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

}