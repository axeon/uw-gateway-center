package uw.gateway.center.controller.root.access;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.common.dto.ResponseData;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.gateway.center.dto.AccessSaasStatsQueryParam;
import uw.gateway.center.entity.AccessSaasStats;


/**
 * saas访问统计。
 */
@RestController
@RequestMapping("/root/access/saasStats")
@Tag(name = "saas访问统计", description = "访问统计增删改查列管理")
@MscPermDeclare(user = UserType.ROOT)
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
    @MscPermDeclare(user = UserType.ROOT, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<AccessSaasStats>> list(AccessSaasStatsQueryParam queryParam){
        AuthServiceHelper.logRef(AccessSaasStats.class);
        return dao.list(AccessSaasStats.class, queryParam);
    }


}