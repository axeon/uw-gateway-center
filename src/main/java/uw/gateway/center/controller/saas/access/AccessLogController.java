package uw.gateway.center.controller.saas.access;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.AuthServiceHelper;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.ActionLog;
import uw.auth.service.constant.AuthType;
import uw.auth.service.constant.UserType;
import uw.dao.DaoManager;
import uw.dao.QueryParam;
import uw.dao.vo.QueryParamResult;
import uw.gateway.center.dto.AccessEsLogQueryParam;
import uw.gateway.center.entity.AccessEsLog;
import uw.log.es.LogClient;
import uw.log.es.vo.ESDataList;

/**
 * 本接口主要查询日志.
 */
@RestController
@RequestMapping("/saas/access/log")
@Tag(name = "访问日志", description = "访问日志")
@MscPermDeclare(user = UserType.SAAS)
public class AccessLogController {

    private final DaoManager dao = DaoManager.getInstance();

    private final LogClient logClient;

    @Autowired
    public AccessLogController(LogClient logClient) {
        this.logClient = logClient;
    }


    /**
     * 访问日志查询
     *
     * @param queryParam
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "访问日志查询", description = "访问日志查询")
    @MscPermDeclare(user = UserType.SAAS, auth = AuthType.PERM, log = ActionLog.REQUEST)
    public ESDataList<AccessEsLog> list(AccessEsLogQueryParam queryParam) {
        AuthServiceHelper.logRef(AccessEsLog.class);
        //钉死关键参数
        queryParam.CLEAR_SORT().ADD_SORT("@timestamp", QueryParam.SORT_DESC);
        queryParam.setSaasId(AuthServiceHelper.getSaasId());
        QueryParamResult result = dao.parseQueryParam(AccessEsLog.class, queryParam);
        String dsl = logClient.translateSqlToDsl(result.genFullSql(), queryParam.START_INDEX(), queryParam.RESULT_NUM(), queryParam.CHECK_AUTO_COUNT());
        String loginLogIndex = "uw.gateway.access.log";
        return logClient.mapQueryResponseToEDataList(logClient.dslQuery(AccessEsLog.class, loginLogIndex, dsl), queryParam.START_INDEX(), queryParam.RESULT_NUM());
    }


}
