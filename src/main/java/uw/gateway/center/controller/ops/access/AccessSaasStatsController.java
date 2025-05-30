package uw.gateway.center.controller.ops.access;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.cache.FusionCache;
import uw.common.app.dto.*;
import uw.common.app.entity.SysCritLog;
import uw.common.app.entity.SysDataHistory;
import uw.common.app.helper.SysDataHistoryHelper;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonResponseCode;
import uw.common.app.constant.CommonState;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.common.dto.ResponseData;
import uw.dao.*;
import uw.gateway.center.dto.AccessSaasStatsQueryParam;
import uw.gateway.center.entity.AccessSaasStats;

import java.util.Date;


/**
 * saas访问统计。
 */
@RestController
@RequestMapping("/ops/access/saasStats")
@Tag(name = "saas访问统计", description = "访问统计增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class AccessSaasStatsController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表访问统计。
     *
     * @param queryParam
     * @return
     *
     */
    @GetMapping("/list")
    @Operation(summary = "列表访问统计", description = "列表访问统计")
    @MscPermDeclare(user = UserType.OPS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<AccessSaasStats>> list(AccessSaasStatsQueryParam queryParam){
        AuthServiceHelper.logRef(AccessSaasStats.class);
        return dao.list(AccessSaasStats.class, queryParam);
    }


}