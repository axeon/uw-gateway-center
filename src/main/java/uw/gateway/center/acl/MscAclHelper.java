package uw.gateway.center.acl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uw.gateway.center.acl.filter.constant.MscAclFilterType;
import uw.gateway.center.acl.filter.vo.MscAclFilterInfo;
import uw.gateway.center.acl.filter.vo.MscAclFilterResult;
import uw.gateway.center.acl.rate.constant.MscAclRateLimitType;
import uw.gateway.center.acl.rate.limiter.FixedWindowRateLimiter;
import uw.gateway.center.acl.rate.util.SaasIdUtils;
import uw.gateway.center.acl.rate.vo.MscAclRateInfo;
import uw.gateway.center.acl.rate.vo.MscAclRateResult;
import uw.auth.client.conf.AuthClientProperties;
import uw.cache.CacheDataLoader;
import uw.cache.FusionCache;
import uw.common.util.IpMatchUtils;

import java.net.InetAddress;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * 访问控制服务。
 */
@Service
public class MscAclHelper {

    private static final Logger log = LoggerFactory.getLogger( MscAclHelper.class );

    /**
     * ip过滤配置缓存名。
     */
    private static final String MSC_ACL_FILTER = "MscAclFilter";

    /**
     * 限流配置缓存名。
     */
    private static final String MSC_ACL_RATE = "MscAclRate";

    /**
     * 限流缓存。
     */
    private static LoadingCache<Long, Cache<String, FixedWindowRateLimiter>> rateLimitCache = null;

    public MscAclHelper(AuthClientProperties authClientProperties, RestTemplate authRestTemplate) {
        //ip过滤配置缓存。
        FusionCache.config( new FusionCache.Config( MSC_ACL_FILTER, 10000, 86400_000L ), new CacheDataLoader<Long, List<MscAclFilterInfo>>() {
            @Override
            public List<MscAclFilterInfo> load(Long saasId) throws Exception {
                URI targetUrl = UriComponentsBuilder.fromUriString( authClientProperties.getAuthCenterHost() ).path( "/rpc/gateway/getAclFilterList" ).queryParam( "saasId",
                        saasId ).build().encode().toUri();
                CompletableFuture<List<MscAclFilterInfo>> future = CompletableFuture.supplyAsync( () -> authRestTemplate.exchange( targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<MscAclFilterInfo>>() {
                } ).getBody() );
                try {
                    return future.get( 60, TimeUnit.SECONDS );
                } catch (Exception e) {
                    log.error( "getAclFilterList error: {}", e.getMessage(), e );
                    return null;
                }
            }
        } );

        //限流配置缓存。
        FusionCache.config( new FusionCache.Config( MSC_ACL_RATE, 10000, 86400_000L ), new CacheDataLoader<Long, List<MscAclRateInfo>>() {
            @Override
            public List<MscAclRateInfo> load(Long saasId) throws Exception {
                URI targetUrl =
                        UriComponentsBuilder.fromUriString( authClientProperties.getAuthCenterHost() ).path( "/rpc/gateway/getAclRateList" ).queryParam( "saasId", saasId ).build().encode().toUri();
                CompletableFuture<List<MscAclRateInfo>> future = CompletableFuture.supplyAsync( () -> authRestTemplate.exchange( targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<MscAclRateInfo>>() {
                } ).getBody() );
                try {
                    return future.get( 60, TimeUnit.SECONDS );
                } catch (Exception e) {
                    log.error( "getAclRateList error: {}", e.getMessage(), e );
                    return null;
                }
            }
        } );

        //限流缓存。第一层缓存10_000,key=saasId，value=Cache。第二层100_000,key=rateKey,value=FixedWindowRateLimiter。
        rateLimitCache =
                Caffeine.newBuilder().maximumSize( 10_000 ).expireAfterAccess( 30, TimeUnit.MINUTES ).build( saasId -> Caffeine.newBuilder().maximumSize( 100_000 ).expireAfterAccess( 30, TimeUnit.MINUTES ).build() );
    }

    /**
     * 限流。
     *
     * @param saasId
     * @param userType
     * @param userId
     * @param uri
     * @param ipPermit
     * @return
     */
    public static MscAclRateResult testRateLimit(long saasId, int userType, long userId, String uri, String ipPermit, int requestsPermit, int bytesPermit) {
        MscAclRateInfo aclRateInfo = MscAclHelper.matchAclRate( saasId, userType, userId, uri );
        if (aclRateInfo != null) {
            int[] rateLimitResult = null;
            //获取限速器实例。
            FixedWindowRateLimiter rateLimiter = MscAclHelper.getRateLimiter( aclRateInfo, saasId, userType, userId, uri, ipPermit );
            if (rateLimiter != null) {
                //限速检测。
                rateLimitResult = rateLimiter.tryAcquire( requestsPermit, bytesPermit );
            }
            return new MscAclRateResult( ipPermit, aclRateInfo, rateLimitResult );
        } else {
            return MscAclRateResult.ALLOWED;
        }
    }

    /**
     * 限流。
     *
     * @param saasId
     * @param userType
     * @param userId
     * @param uri
     * @param ipPermit
     * @return
     */
    public static MscAclRateResult rateLimit(long saasId, int userType, long userId, String uri, String ipPermit, int requestsPermit, int bytesPermit) {
        MscAclRateInfo aclRateInfo = MscAclHelper.matchAclRate( saasId, userType, userId, uri );
        if (aclRateInfo != null) {
            int[] rateLimitResult;
            //获取限速器实例。
            FixedWindowRateLimiter rateLimiter = MscAclHelper.getRateLimiter( aclRateInfo, saasId, userType, userId, uri, ipPermit );
            if (rateLimiter != null) {
                //限速检测。
                rateLimitResult = rateLimiter.tryAcquire( requestsPermit, bytesPermit );
                if (rateLimitResult != null) {
                    return new MscAclRateResult( ipPermit, aclRateInfo, rateLimitResult );
                }
            }
        }
        return MscAclRateResult.ALLOWED;
    }

    /**
     * 获取rateLimiter限速器。
     *
     * @param aclRate
     * @param saasId
     * @param userType
     * @param userId
     * @param uri
     * @param ipPermit
     * @return
     */
    public static FixedWindowRateLimiter getRateLimiter(MscAclRateInfo aclRate, long saasId, int userType, long userId, String uri, String ipPermit) {
        //计算rateKey。
        String rateKey = null;
        if (aclRate.getLimitType() == MscAclRateLimitType.IP.getValue()) {
            rateKey = aclRate.getId() + ":" + ipPermit;
        } else if (aclRate.getLimitType() == MscAclRateLimitType.SAAS_LEVEL.getValue()) {
            rateKey = aclRate.getId() + ":" + SaasIdUtils.getVipLevel( saasId );
        } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_TYPE.getValue()) {
            rateKey = aclRate.getId() + ":" + userType;
        } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_ID.getValue()) {
            rateKey = aclRate.getId() + ":" + userId;
        } else if (aclRate.getLimitType() == MscAclRateLimitType.SAAS_LEVEL_URI.getValue()) {
            rateKey = aclRate.getId() + ":" + SaasIdUtils.getVipLevel( saasId ) + ":" + uri;
        } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_TYPE_URI.getValue()) {
            rateKey = aclRate.getId() + ":" + userType + ":" + uri;
        } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_ID_URI.getValue()) {
            rateKey = aclRate.getId() + ":" + userId + ":" + uri;
        }
        if (rateKey == null) {
            return null;
        }
        return rateLimitCache.get( aclRate.getSaasId() ).get( rateKey, x -> new FixedWindowRateLimiter( aclRate.getLimitSeconds() * 1000L, aclRate.getLimitRequests(),
                aclRate.getLimitBytes() ) );
    }

    /**
     * 判断是否过滤IP。
     *
     * @param saasId
     * @param userType
     * @param userId
     * @param inetAddress
     * @return
     */
    public static MscAclFilterResult testFilterIp(long saasId, int userType, long userId, InetAddress inetAddress) {
        MscAclFilterInfo filterInfo = matchAclFilter( saasId, userType, userId );
        //没有过滤配置，则直接返回true。
        if (filterInfo != null) {
            boolean allowed = true;
            if (filterInfo.getFilterType() == MscAclFilterType.ALLOW_LIST.getValue() && !IpMatchUtils.matches( filterInfo.getIpRangeList(), inetAddress )) {
                //白名单匹配失败。
                allowed = false;
            } else if (filterInfo.getFilterType() == MscAclFilterType.DENY_LIST.getValue() && IpMatchUtils.matches( filterInfo.getIpRangeList(), inetAddress )) {
                //黑名单匹配成功。
                allowed = false;
            }
            return new MscAclFilterResult( inetAddress.getHostAddress(), filterInfo, allowed );
        }
        return MscAclFilterResult.ALLOWED;
    }

    /**
     * 判断是否过滤IP。
     *
     * @param saasId
     * @param userType
     * @param userId
     * @param ipStr
     * @return
     */
    public static MscAclFilterResult filterIp(long saasId, int userType, long userId, String ipStr, InetAddress inetAddress) {
        MscAclFilterInfo filterInfo = matchAclFilter( saasId, userType, userId );
        //没有过滤配置，则直接返回true。
        if (filterInfo != null) {
            if (filterInfo.getFilterType() == MscAclFilterType.ALLOW_LIST.getValue() && !IpMatchUtils.matches( filterInfo.getIpRangeList(), inetAddress )) {
                //白名单匹配失败。
                return new MscAclFilterResult( ipStr, filterInfo );
            } else if (filterInfo.getFilterType() == MscAclFilterType.DENY_LIST.getValue() && IpMatchUtils.matches( filterInfo.getIpRangeList(), inetAddress )) {
                //黑名单匹配成功。
                return new MscAclFilterResult( ipStr, filterInfo );
            }
        }
        return MscAclFilterResult.ALLOWED;
    }

    /**
     * 判断是否过滤IP。
     *
     * @param saasId
     * @param userType
     * @param userId
     * @param ipStr
     * @return
     */
    public static MscAclFilterResult filterIp(long saasId, int userType, long userId, String ipStr) {
        MscAclFilterInfo filterInfo = matchAclFilter( saasId, userType, userId );
        //没有过滤配置，则直接返回true。
        if (filterInfo != null) {
            if (filterInfo.getFilterType() == MscAclFilterType.ALLOW_LIST.getValue() && !IpMatchUtils.matches( filterInfo.getIpRangeList(), ipStr )) {
                //白名单匹配失败。
                return new MscAclFilterResult( ipStr, filterInfo );
            } else if (filterInfo.getFilterType() == MscAclFilterType.DENY_LIST.getValue() && IpMatchUtils.matches( filterInfo.getIpRangeList(), ipStr )) {
                //黑名单匹配成功。
                return new MscAclFilterResult( ipStr, filterInfo );
            }
        }
        return MscAclFilterResult.ALLOWED;
    }

    /**
     * 刷新IP过滤器缓存。
     *
     * @param saasId
     */
    public static void refreshAclFilterCache(Long saasId) {
        FusionCache.refresh( MSC_ACL_FILTER, saasId );
    }

    /**
     * 刷新流控缓存。
     *
     * @param saasId
     */
    public static void refreshAclRateCache(Long saasId) {
        FusionCache.refresh( MSC_ACL_RATE, saasId );
    }

    /**
     * 作废IP过滤器缓存。
     *
     * @param saasId
     */
    public static void invalidateAclFilterCache(Long saasId) {
        FusionCache.invalidate( MSC_ACL_FILTER, saasId );
    }

    /**
     * 作废流控缓存。
     *
     * @param saasId
     */
    public static void invalidateAclRateCache(Long saasId) {
        FusionCache.invalidate( MSC_ACL_RATE, saasId );
    }

    /**
     * 匹配流控规则。
     *
     * @param saasId
     * @param userType
     * @param userId
     * @param uri
     * @return
     */
    public static MscAclRateInfo matchAclRate(long saasId, int userType, long userId, String uri) {
        MscAclRateInfo aclRate = null;
        //先匹配saas过滤器。
        if (saasId > -1L) {
            aclRate = matchAclRate( getAclRateList( saasId ), saasId, userType, userId, uri );
        }
        //匹配系统过滤器。
        if (aclRate == null) {
            aclRate = matchAclRate( getAclRateList( -1L ), saasId, userType, userId, uri );
        }
        return aclRate;
    }

    /**
     * 匹配acl过滤器。
     *
     * @param saasId
     * @return
     */
    private static MscAclFilterInfo matchAclFilter(long saasId, int userType, long userId) {
        MscAclFilterInfo filterInfo = null;
        //先匹配saas过滤器。
        if (saasId > -1L) {
            filterInfo = matchAclFilter( getAclFilterList( saasId ), userType, userId );
        }
        //匹配系统过滤器。
        if (filterInfo == null) {
            filterInfo = matchAclFilter( getAclFilterList( -1L ), userType, userId );
        }
        return filterInfo;
    }

    /**
     * 匹配流控规则。
     *
     * @param rateList
     * @param saasId
     * @param userType
     * @param userId
     * @return
     */
    private static MscAclRateInfo matchAclRate(List<MscAclRateInfo> rateList, long saasId, int userType, long userId, String uri) {
        //如果没有数值，直接返回null。
        if (rateList == null || rateList.isEmpty()) {
            return null;
        }
        //优先级匹配。
        for (MscAclRateInfo aclRate : rateList) {
            if (aclRate.getLimitType() == MscAclRateLimitType.NONE.getValue()) {
                return aclRate;
            } else if (aclRate.getLimitType() == MscAclRateLimitType.IP.getValue()) {
                return aclRate;
            } else if (aclRate.getLimitType() == MscAclRateLimitType.SAAS_LEVEL.getValue()) {
                if (aclRate.getSaasLevel() == -1 || aclRate.getSaasLevel() == SaasIdUtils.getVipLevel( saasId )) {
                    return aclRate;
                }
            } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_TYPE.getValue()) {
                if (aclRate.getUserType() == -1 || aclRate.getUserType() == userType) {
                    return aclRate;
                }
            } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_ID.getValue()) {
                if (aclRate.getUserId() == -1 || aclRate.getUserId() == userId) {
                    return aclRate;
                }
            } else if (aclRate.getLimitType() == MscAclRateLimitType.SAAS_LEVEL_URI.getValue()) {
                if ((aclRate.getSaasLevel() == -1 || aclRate.getSaasLevel() == SaasIdUtils.getVipLevel( saasId )) && (StringUtils.equals( uri, aclRate.getLimitUri() ) || StringUtils.startsWith( uri, aclRate.getLimitUri() ))) {
                    return aclRate;
                }
            } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_TYPE_URI.getValue()) {
                if ((aclRate.getUserType() == -1 || aclRate.getUserType() == userType) && (StringUtils.equals( uri, aclRate.getLimitUri() ) || StringUtils.startsWith( uri,
                        aclRate.getLimitUri() ))) {
                    return aclRate;
                }
            } else if (aclRate.getLimitType() == MscAclRateLimitType.USER_ID_URI.getValue()) {
                if ((aclRate.getUserId() == -1 || aclRate.getUserId() == userId) && aclRate.getUserId() == userId && (StringUtils.equals( uri, aclRate.getLimitUri() ) || StringUtils.startsWith( uri, aclRate.getLimitUri() ))) {
                    return aclRate;
                }
            }
        }
        return null;
    }

    /**
     * 匹配acl过滤器。
     *
     * @return
     */
    private static MscAclFilterInfo matchAclFilter(List<MscAclFilterInfo> filterList, int userType, long userId) {
        //如果没有数值，直接返回null。
        if (filterList == null || filterList.isEmpty()) {
            return null;
        }
        //优先级匹配。
        for (MscAclFilterInfo filterInfo : filterList) {
            //首先匹配userId
            if (filterInfo.getUserId() > -1 && filterInfo.getUserId() == userId) {
                return filterInfo;
            }
            //其次匹配userType。
            if (filterInfo.getUserType() > -1 && filterInfo.getUserType() == userType) {
                return filterInfo;
            }
        }
        //最后确认默认值
        MscAclFilterInfo filterInfo = filterList.getLast();
        if (filterInfo.getUserId() == -1L && filterInfo.getUserType() == -1) {
            return filterInfo;
        } else {
            return null;
        }
    }

    /**
     * 获取IP过滤器列表。
     *
     * @param saasId
     * @return
     */
    private static List<MscAclFilterInfo> getAclFilterList(long saasId) {
        return FusionCache.get( MSC_ACL_FILTER, saasId );
    }

    /**
     * 获取流控列表。
     *
     * @param saasId
     * @return
     */
    private static List<MscAclRateInfo> getAclRateList(long saasId) {
        return FusionCache.get( MSC_ACL_RATE, saasId );
    }

}
