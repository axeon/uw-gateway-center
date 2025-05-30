package uw.gateway.center.controller.admin.home;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.dto.ResponseData;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.gateway.center.dto.AccessGlobalStatsQueryParam;
import uw.gateway.center.entity.AccessGlobalStats;
import uw.gateway.center.helper.AccessLogStatsHelper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/home/dashboard")
@Tag(name = "仪表盘", description = "仪表盘")
@MscPermDeclare(user = UserType.ADMIN)
public class DashboardController {
    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表全局统计信息。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/listGlobalStats")
    @Operation(summary = "列表全局统计信息", description = "列表全局统计信息")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.NONE)
    public ResponseData<DataList<AccessGlobalStats>> listGlobalStats(AccessGlobalStatsQueryParam queryParam) {
        return dao.list(AccessGlobalStats.class, queryParam);
    }


    /**
     * 今日全局统计。
     *
     * @return
     */
    @GetMapping("/todayGlobalStats")
    @Operation(summary = "今日全局统计", description = "今日全局统计")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.NONE)
    public ResponseData<AccessGlobalStats> todayGlobalStats() {
        return AccessLogStatsHelper.todayGlobalStats();
    }


    /**
     * 全局分时指标。
     *
     * @return
     */
    @GetMapping("/latestGlobalMetric")
    @Operation(summary = "全局分时指标", description = "全局分时指标")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.NONE)
    public ResponseData<Map<String, Map<String, Double>>> latestGlobalMetric() {
        return AccessLogStatsHelper.latestGlobalMetric();
    }


    /**
     * 全局状态码统计。
     *
     * @return
     */
    @GetMapping("/latestGlobalCode")
    @Operation(summary = "全局状态码统计", description = "全局状态码统计")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.NONE)
    public ResponseData<Map<String, List<Map<String, Object>>>> latestGlobalCode() {
        return AccessLogStatsHelper.latestGlobalCode();
    }


    /**
     * 全局排行榜。
     *
     * @return
     */
    @GetMapping("/latestGlobalTop")
    @Operation(summary = "全局排行榜", description = "全局排行榜")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.NONE)
    public ResponseData<Map<String, List<Map<String, Object>>>> latestGlobalTop() {
        return AccessLogStatsHelper.latestGlobalTop();
    }

}
