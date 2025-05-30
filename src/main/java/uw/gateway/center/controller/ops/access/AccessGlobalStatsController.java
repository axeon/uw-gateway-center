package uw.gateway.center.controller.ops.access;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import uw.cache.FusionCache;
import uw.common.app.dto.AuthIdQueryParam;
import uw.common.app.dto.IdStateQueryParam;
import uw.common.app.dto.SysCritLogQueryParam;
import uw.common.app.dto.SysDataHistoryQueryParam;
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
import uw.gateway.center.dto.AccessGlobalStatsQueryParam;
import uw.gateway.center.entity.AccessGlobalStats;

import java.util.Date;


/**
 * 全局访问统计。
 */
@RestController
@RequestMapping("/ops/access/globalStats")
@Tag(name = "全局访问统计", description = "访问统计增删改查列管理")
@MscPermDeclare(user = UserType.OPS)
public class AccessGlobalStatsController {

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
    public ResponseData<DataList<AccessGlobalStats>> list(AccessGlobalStatsQueryParam queryParam){
        AuthServiceHelper.logRef(AccessGlobalStats.class);
        return dao.list(AccessGlobalStats.class, queryParam);
    }

}