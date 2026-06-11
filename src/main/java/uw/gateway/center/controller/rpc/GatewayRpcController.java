package uw.gateway.center.controller.rpc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.UserType;
import uw.common.app.constant.CommonState;
import uw.common.response.ResponseData;
import uw.common.util.IpMatchUtils;
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.gateway.center.acl.filter.vo.MscAclFilterInfo;
import uw.gateway.center.acl.rate.vo.MscAclRateInfo;
import uw.gateway.center.constant.AclAuditState;
import uw.gateway.center.entity.MscAclFilter;
import uw.gateway.center.entity.MscAclFilterData;
import uw.gateway.center.entity.MscAclRate;
import uw.gateway.center.entity.MscAcmeCert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gateway RPC Controller
 */
@RestController
@Tag(name = "网关RPC接口")
@RequestMapping("/rpc/gateway")
public class GatewayRpcController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRpcController.class);

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 获取IP过滤器列表。
     */
    @GetMapping("/getAclFilterList")
    @Operation(summary = "获取IP过滤器列表", description = "获取IP过滤器列表")
    @MscPermDeclare(user = UserType.RPC)
    public ResponseData<List<MscAclFilterInfo>> getAclFilterList(long saasId) {
        return dao.list(MscAclFilter.class, "SELECT id,user_type,user_id,filter_type,filter_name from msc_acl_filter where saas_id=? and state=? and audit_state=? order by user_type desc,user_id desc",
                new Object[]{saasId, CommonState.ENABLED.getValue(), AclAuditState.CONFIRM.getValue()}).onSuccess(filterList -> {
            if (filterList.size() == 0) {
                return ResponseData.success(List.of());
            }
            String filterIds = filterList.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.joining(","));
            return dao.list(MscAclFilterData.class, "SELECT filter_id,ip_start,ip_end from msc_acl_filter_data where filter_id in (" + filterIds + ") and state=? and audit_state=? order by ip_start asc",
                    new Object[]{CommonState.ENABLED.getValue(), AclAuditState.CONFIRM.getValue()}).onSuccess(dataList -> {
                List<MscAclFilterInfo> filterInfoList = new ArrayList<>(filterList.size());
                for (MscAclFilter aclFilter : filterList) {
                    MscAclFilterInfo info = new MscAclFilterInfo(aclFilter.getId(), aclFilter.getSaasId(), aclFilter.getUserId(), aclFilter.getUserType(), aclFilter.getFilterType(), aclFilter.getFilterName());
                    info.setIpRangeList(dataList.stream().filter(x -> x.getFilterId() == aclFilter.getId()).map(x -> new IpMatchUtils.IpRange(x.getIpInfo())).collect(Collectors.toList()));
                    filterInfoList.add(info);
                }
                return ResponseData.success(filterInfoList);
            });
        });
    }

    /**
     * 获取流控列表。
     */
    @GetMapping("/getAclRateList")
    @Operation(summary = "获取流控列表", description = "获取流控列表")
    @MscPermDeclare(user = UserType.RPC)
    public ResponseData<List<MscAclRateInfo>> getAclRateList(long saasId) {
        return dao.list(MscAclRate.class, "SELECT id,user_type,user_id,limit_name,limit_type,limit_uri,limit_seconds,limit_requests,limit_bytes from msc_acl_rate where saas_id=? and state=? and audit_state=? order by limit_type desc, saas_level desc, user_type desc, user_id desc",
                new Object[]{saasId, CommonState.ENABLED.getValue(), AclAuditState.CONFIRM.getValue()}).onSuccess(rateList -> {
            List<MscAclRateInfo> rateInfoList = new ArrayList<>(rateList.size());
            for (MscAclRate aclRate : rateList) {
                rateInfoList.add(new MscAclRateInfo(aclRate.getId(), aclRate.getSaasLevel(), aclRate.getSaasId(), aclRate.getUserId(), aclRate.getUserType(), aclRate.getLimitName(), aclRate.getLimitType(), aclRate.getLimitUri(), aclRate.getLimitSeconds(), aclRate.getLimitRequests(), aclRate.getLimitBytes()));
            }
            return ResponseData.success(rateInfoList);
        });
    }

    /**
     * 获取ssl证书列表。
     */
    @GetMapping("/getSslCertList")
    @Operation(summary = "获取ssl证书列表", description = "获取ssl证书列表")
    @MscPermDeclare(user = UserType.RPC)
    public ResponseData<List<MscAcmeCert>> getSslCertList() {
        Date now = SystemClock.nowDate();
        String sql = "SELECT * from msc_acme_cert where state=? and expire_date>=? and active_date<=? order by id desc";
        return dao.list(MscAcmeCert.class, sql, new Object[]{CommonState.ENABLED.getValue(), now, now}).onSuccess(pageList -> {
            // 排重后获取每个域名的最新证书。
            var list = new ArrayList<>(pageList.stream().collect(Collectors.toMap(MscAcmeCert::getDomainId, x -> x, (existingValue, newValue) -> {
                if (existingValue.getExpireDate().compareTo(newValue.getExpireDate()) > 0) {
                    return existingValue;
                } else {
                    return newValue;
                }
            })).values());
            //最后按照domainId做asc排序，让高优先级的域名优先使用
            list.sort((o1, o2) -> Math.toIntExact(o1.getDomainId() - o2.getDomainId()));
            return ResponseData.success(list);
        });
    }

}
