package uw.gateway.center.controller.ops.acme;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.gateway.center.acme.AcmeHelper;
import uw.gateway.center.dto.MscAcmeCertQueryParam;
import uw.gateway.center.entity.MscAcmeCert;
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
import uw.gateway.center.entity.MscAcmeDomain;

import java.util.Date;


/**
 * ACME证书管理。
 */
@RestController
@RequestMapping("/ops/acme/domain/cert")
@Tag(name = "ACME证书管理", description = "acme证书增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class MscAcmeCertController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表acme证书。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "列表acme证书", description = "列表acme证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<MscAcmeCert>> list(MscAcmeCertQueryParam queryParam) {
        AuthServiceHelper.logRef(MscAcmeCert.class);
        return dao.list(MscAcmeCert.class, queryParam);
    }

    /**
     * 轻量级列表acme证书，一般用于select控件。
     *
     * @return
     */
    @GetMapping("/liteList")
    @Operation(summary = "轻量级列表acme证书", description = "轻量级列表acme证书，一般用于select控件。")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.USER, log = ActionLog.NONE)
    public ResponseData<DataList<MscAcmeCert>> liteList(MscAcmeCertQueryParam queryParam) {
        queryParam.SELECT_SQL("SELECT id,saas_id,domain_id,domain_name,domain_alias,cert_alg,active_date,expire_date,create_date,modify_date,state from msc_acme_cert ");
        return dao.list(MscAcmeCert.class, queryParam);
    }

    /**
     * 加载acme证书。
     *
     * @param id
     */
    @GetMapping("/load")
    @Operation(summary = "加载acme证书", description = "加载acme证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<MscAcmeCert> load(@Parameter(description = "主键ID", required = true) @RequestParam long id) {
        AuthServiceHelper.logRef(MscAcmeCert.class, id);
        return dao.queryForSingleObject(MscAcmeCert.class, new AuthIdQueryParam(id));
    }

    /**
     * 新增acme证书。
     *
     * @param mscAcmeCert
     * @return
     *
     */
    @PostMapping("/save")
    @Operation(summary = "新增acme证书", description = "新增acme证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData<MscAcmeCert> save(@RequestBody MscAcmeCert mscAcmeCert){
        long id = dao.getSequenceId(MscAcmeCert.class);
        AuthServiceHelper.logRef(MscAcmeCert.class,id);
        MscAcmeDomain mscAcmeDomain = dao.load(MscAcmeDomain.class, mscAcmeCert.getDomainId()).getData();
        if (mscAcmeDomain == null){
            return ResponseData.errorMsg("域名不存在");
        }
        mscAcmeCert.setId(id);
        mscAcmeCert.setDomainName(mscAcmeDomain.getDomainName());
        mscAcmeCert.setDomainAlias(mscAcmeDomain.getDomainAlias());
        mscAcmeCert.setCreateDate(new Date());
        mscAcmeCert.setModifyDate(null);
        mscAcmeCert.setState(CommonState.DISABLED.getValue());
        return dao.save( mscAcmeCert ).onSuccess(savedEntity -> {
            //保存历史记录
            SysDataHistoryHelper.saveHistory(mscAcmeCert);
        });
    }

    /**
     * 启用acme证书。
     *
     * @param id
     */
    @PutMapping("/enable")
    @Operation(summary = "启用acme证书", description = "启用acme证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData enable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeCert.class, id, remark);
        return dao.update(new MscAcmeCert().modifyDate(new Date()).state(CommonState.ENABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue())).onSuccess(updatedEntity -> {
            dao.load(MscAcmeCert.class, id).onSuccess(mscAcmeCert -> {
                AcmeHelper.updateDomainCertInfo(mscAcmeCert.getDomainId());
            });
        });
    }

    /**
     * 禁用acme证书。
     *
     * @param id
     */
    @PutMapping("/disable")
    @Operation(summary = "禁用acme证书", description = "禁用acme证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData disable(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeCert.class, id, remark);
        return dao.update(new MscAcmeCert().modifyDate(new Date()).state(CommonState.DISABLED.getValue()), new AuthIdStateQueryParam(id, CommonState.ENABLED.getValue())).onSuccess(updatedEntity -> {
            dao.load(MscAcmeCert.class, id).onSuccess(mscAcmeCert -> {
                AcmeHelper.updateDomainCertInfo(mscAcmeCert.getDomainId());
            });
        });
    }

    /**
     * 删除acme证书。
     *
     * @param id
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除acme证书", description = "删除acme证书")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.CRIT)
    public ResponseData delete(@Parameter(description = "主键ID") @RequestParam long id, @Parameter(description = "备注") @RequestParam String remark) {
        AuthServiceHelper.logInfo(MscAcmeCert.class, id, remark);
        return dao.update(new MscAcmeCert().modifyDate(new Date()).state(CommonState.DELETED.getValue()), new AuthIdStateQueryParam(id, CommonState.DISABLED.getValue()));
    }

}