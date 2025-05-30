package uw.gateway.center.controller.admin.log;

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
import uw.common.app.dto.SysCritLogQueryParam;
import uw.common.app.entity.SysCritLog;
import uw.common.dto.ResponseData;
import uw.dao.DaoManager;
import uw.dao.DataList;
import uw.dao.QueryParam;


/**
 * msc关键日志增删改查列管理。
 */
@RestController
@RequestMapping("/admin/log/critLog")
@Tag(name = "关键日志", description = "关键日志")
@MscPermDeclare(user = UserType.ADMIN)
public class SysCritLogController {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 列表msc关键日志。
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "关键日志查询", description = "关键日志查询")
    @MscPermDeclare(user = UserType.ADMIN, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ResponseData<DataList<SysCritLog>> list(SysCritLogQueryParam queryParam) {
        AuthServiceHelper.logRef(SysCritLog.class);
        //钉死关键参数
        queryParam.setUserType(UserType.ADMIN.getValue());
        queryParam.setSaasId(0L);
        queryParam.setMchId(0L);
        queryParam.CLEAR_SORT().ADD_SORT("id", QueryParam.SORT_DESC);
        return dao.list(SysCritLog.class, queryParam);
    }


}