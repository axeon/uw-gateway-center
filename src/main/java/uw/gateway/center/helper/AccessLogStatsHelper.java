package uw.gateway.center.helper;

import com.fasterxml.jackson.databind.JavaType;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uw.cache.CacheDataLoader;
import uw.cache.FusionCache;
import uw.common.dto.ResponseData;
import uw.common.util.DateUtils;
import uw.common.util.JsonUtils;
import uw.common.util.SystemClock;
import uw.gateway.center.conf.GatewayCenterProperties;
import uw.gateway.center.constant.GatewayCenterConstants;
import uw.gateway.center.entity.AccessGlobalStats;
import uw.gateway.center.entity.AccessSaasStats;
import uw.httpclient.http.HttpConfig;
import uw.httpclient.http.HttpInterface;
import uw.httpclient.json.JsonInterfaceHelper;
import uw.httpclient.util.MediaTypes;
import uw.log.es.vo.SearchResponse;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 访问日志统计帮助类
 */
@Service
public class AccessLogStatsHelper {

    private static final Logger log = LoggerFactory.getLogger(AccessLogStatsHelper.class);
    /**
     * 日期格式。
     */
    private static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");

    /**
     * ES查询接口。
     */
    private static final HttpInterface HTTP_INTERFACE = new JsonInterfaceHelper(HttpConfig.builder().retryOnConnectionFailure(true).connectTimeout(60_000L).readTimeout(180_000L).writeTimeout(60_000L).hostnameVerifier((hostName, sslSession) -> true).build());

    /**
     * 基于saas的分组指标统计dsl。
     */
    private static final String SAAS_STATS_DSL = """
            {
               "size": 0,
               "query": {
                 "range": {
                  "@timestamp": {
                    "gte": "%s",
                    "lte": "%s"
                   }
                 }
               },
               "aggs": {
                 "saasStats": {
                   "terms": {
                     "field": "saasId",
                     "size": 1000000
                   },
                   "aggs": {
                     "userNum": {
                       "cardinality": {
                         "field": "userId"
                       }
                     },
                     "ipNum": {
                       "cardinality": {
                         "field": "userIp.keyword"
                       }
                     },
                     "serviceNum": {
                       "cardinality": {
                         "field": "serviceName.keyword"
                       }
                     },
                     "apiNum": {
                       "cardinality": {
                         "field": "requestPath.keyword"
                       }
                     },
                     "sessionNum": {
                       "cardinality": {
                         "field": "requestId.keyword"
                       }
                     },
                     "requestNum": {
                       "value_count": {
                         "field": "_index"
                       }
                     },
                     "requestSize": {
                       "sum": {
                         "field": "requestSize"
                       }
                     },
                     "responseSize": {
                       "sum": {
                         "field": "responseSize"
                       }
                     },
                     "responseMillis": {
                       "sum": {
                         "field": "responseMillis"
                       }
                     },
                     "responseCode": {
                       "terms": {
                         "field": "responseCode",
                         "size": 10
                       }
                     }
                   }
                 }
               }
             }
            """;

    /**
     * 全局指标统计dsl。
     */
    private static final String GLOBAL_STATS_DSL = """
            {
               "size": 0,
               "query": {
                 "range": {
                  "@timestamp": {
                    "gte": "%s",
                    "lte": "%s"
                   }
                 }
               },
               "aggs": {
                 "saasNum": {
                   "cardinality": {
                     "field": "saasId"
                   }
                 },
                 "userNum": {
                   "cardinality": {
                     "field": "userId"
                   }
                 },
                 "ipNum": {
                   "cardinality": {
                     "field": "userIp.keyword"
                   }
                 },
                 "serviceNum": {
                   "cardinality": {
                     "field": "serviceName.keyword"
                   }
                 },
                 "apiNum": {
                   "cardinality": {
                     "field": "requestPath.keyword"
                   }
                 },
                 "sessionNum": {
                   "cardinality": {
                     "field": "requestId.keyword"
                   }
                 },
                 "requestNum": {
                   "value_count": {
                     "field": "_index"
                   }
                 },
                 "requestSize": {
                   "sum": {
                     "field": "requestSize"
                   }
                 },
                 "responseSize": {
                   "sum": {
                     "field": "responseSize"
                   }
                 },
                 "responseMillis": {
                   "sum": {
                     "field": "responseMillis"
                   }
                 },
                 "response": {
                   "terms": {
                     "field": "responseCode",
                     "size": 10
                   }
                 }
               }
             }
            """;

    /**
     * 全局5m分时统计数据。
     */
    private static final String GLOBAL_METRICS_DSL = """
            {
              "size": 0,
              "query": {
                "range": {
                  "@timestamp": {
                    "gte": "%s",
                    "lte": "%s"
                  }
                }
              },
              "aggs": {
                "histogram": {
                  "date_histogram": {
                    "field": "@timestamp",
                    "fixed_interval": "%sm",
                    "format": "yyyy-MM-dd HH:mm"
                  },
                  "aggs": {
                    "saasNum": {
                      "cardinality": {
                        "field": "saasId"
                      }
                    },
                    "userNum": {
                      "cardinality": {
                        "field": "userId"
                      }
                    },
                    "ipNum": {
                      "cardinality": {
                        "field": "userIp.keyword"
                      }
                    },
                    "appNum": {
                      "cardinality": {
                        "field": "serviceName.keyword"
                      }
                    },
                    "apiNum": {
                      "cardinality": {
                        "field": "requestPath.keyword"
                      }
                    },
                    "sessionNum": {
                      "cardinality": {
                        "field": "requestId.keyword"
                      }
                    },
                    "requestNum": {
                      "value_count": {
                        "field": "_index"
                      }
                    },
                    "requestSize": {
                      "sum": {
                        "field": "requestSize"
                      }
                    },
                    "responseSize": {
                      "sum": {
                        "field": "responseSize"
                      }
                    },
                    "responseMillis": {
                      "sum": {
                        "field": "responseMillis"
                      }
                    },
                    "response": {
                      "terms": {
                        "field": "responseCode",
                        "size": 10
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * 获取全局top100的dsl。
     */
    private static final String GLOBAL_TOP_DSL = """
            {
              "size": 0,
              "query": {
                "range": {
                  "@timestamp": {
                    "gte": "%s",
                    "lte": "%s"
                  }
                }
              },
              "aggs": {
                %s
              }
            }
            """;
    /**
     * 全局状态码统计的dsl。
     */
    private static final String GLOBAL_CODE_DSL = """
            {
              "size": 0,
              "query": {
                "bool": {
                  "must": {
                    "range": {
                      "@timestamp": {
                        "gte": "%s",
                        "lte": "%s"
                      }
                    }
                  },
                  "must_not": {
                    "term": {
                      "responseCode": 200
                    }
                  }
                }
              },
              "aggs": {
                "codeStats": {
                  "terms": {
                    "field": "responseCode",
                    "size": 100,
                    "order": {
                      "_count": "desc"
                    },
                    "min_doc_count": 10
                  },
                  "aggs": {
                    "requestPath": {
                      "terms": {
                        "field": "requestPath.keyword",
                        "size": 100,
                        "order": {
                          "_count": "desc"
                        },
                        "min_doc_count": 10
                      }
                    },
                    "userIp": {
                      "terms": {
                        "field": "userIp.keyword",
                        "size": 100,
                        "order": {
                          "_count": "desc"
                        },
                        "min_doc_count": 10
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * 全局Code的CACHE。
     */
    private static final String GLOBAL_CODE_CACHE = "globalCode";
    /**
     * 全局top100的CACHE。
     */
    private static final String GLOBAL_TOP_CACHE = "globalTop";
    /**
     * 全局Metric的CACHE。
     */
    private static final String GLOBAL_METRICS_CACHE = "globalMetrics";
    /**
     * 全局Stats的CACHE。
     */
    private static final String GLOBAL_STATS_CACHE = "globalStats";
    /**
     * 网关中心配置文件。
     */
    private static GatewayCenterProperties gatewayCenterProperties;
    /**
     * ES配置。
     */
    private static GatewayCenterProperties.EsConfig esConfig;


    /**
     * 构造函数。
     *
     * @param gatewayCenterProperties
     */


    /**
     * 构造函数。
     *
     * @param gatewayCenterProperties
     */
    public AccessLogStatsHelper(GatewayCenterProperties gatewayCenterProperties) {
        AccessLogStatsHelper.gatewayCenterProperties = gatewayCenterProperties;
        AccessLogStatsHelper.esConfig = gatewayCenterProperties.getAccessLogEs();
        //stats缓存。
        FusionCache.config(FusionCache.Config.builder().cacheName(GLOBAL_STATS_CACHE).localCacheMaxNum(1000).localCacheExpireMillis(300_000L).globalCacheExpireMillis(300_000L).autoNotifyInvalidate(true).build(), new CacheDataLoader<String, ResponseData<AccessGlobalStats>>() {
            @Override
            public ResponseData<AccessGlobalStats> load(String name) throws Exception {
                Date now = SystemClock.nowDate();
                Date startDate = DateUtils.beginOfToday(now);
                return AccessLogStatsHelper.globalStats(startDate, now).onSuccess(stats -> {
                    stats.setCreateDate(now);
                    stats.setStatsDate(startDate);
                });
            }
        });
        //metric缓存。
        FusionCache.config(FusionCache.Config.builder().cacheName(GLOBAL_METRICS_CACHE).localCacheMaxNum(1000).localCacheExpireMillis(300_000L).globalCacheExpireMillis(300_000L).autoNotifyInvalidate(true).build(), new CacheDataLoader<String, ResponseData<Map<String, Map<String, Double>>>>() {
            @Override
            public ResponseData<Map<String, Map<String, Double>>> load(String name) throws Exception {
                Date now = SystemClock.nowDate();
                Date startDate = new Date(now.getTime() - 1000 * 60 * 60 * 24);
                return AccessLogStatsHelper.globalMetrics(startDate, now, 5);
            }
        });

        //CODE  缓存。
        FusionCache.config(FusionCache.Config.builder().cacheName(GLOBAL_CODE_CACHE).localCacheMaxNum(1000).localCacheExpireMillis(300_000L).globalCacheExpireMillis(300_000L).autoNotifyInvalidate(true).build(), new CacheDataLoader<String, ResponseData<Map<String, List<Map<String, Object>>>>>() {
            @Override
            public ResponseData<Map<String, List<Map<String, Object>>>> load(String name) throws Exception {
                Date now = SystemClock.nowDate();
                Date startDate = new Date(now.getTime() - 1000 * 60 * 60 * 24);
                return AccessLogStatsHelper.globalCodeData(startDate, now);
            }
        });

        //top缓存。
        FusionCache.config(FusionCache.Config.builder().cacheName(GLOBAL_TOP_CACHE).localCacheMaxNum(1000).localCacheExpireMillis(300_000L).globalCacheExpireMillis(300_000L).autoNotifyInvalidate(true).build(), new CacheDataLoader<String, ResponseData<Map<String, List<Map<String, Object>>>>>() {
            @Override
            public ResponseData<Map<String, List<Map<String, Object>>>> load(String name) throws Exception {
                Date now = SystemClock.nowDate();
                Date startDate = new Date(now.getTime() - 1000 * 60 * 60 * 24);
                List<AccessLogStatsHelper.TopParam> topParamList = new ArrayList<>();
                topParamList.add(new AccessLogStatsHelper.TopParam("topSaas", "saasId", "_count", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topSaasByTime", "saasId", "responseMillis", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topSaasBySize", "saasId", "responseSize", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topUser", "userId", "_count", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topUserByTime", "userId", "responseMillis", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topUserBySize", "userId", "responseSize", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topIp", "userIp.keyword", "_count", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topIpByTime", "userIp.keyword", "responseMillis", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topIpBySize", "userIp.keyword", "responseSize", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topService", "serviceName.keyword", "_count", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topServiceByTime", "serviceName.keyword", "responseMillis", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topServiceBySize", "serviceName.keyword", "responseSize", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topApi", "requestPath.keyword", "_count", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topApiByTime", "requestPath.keyword", "responseMillis", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topApiBySize", "requestPath.keyword", "responseSize", 100, 10));
                topParamList.add(new AccessLogStatsHelper.TopParam("topCode", "responseCode", "_count", 100, 10));
                return AccessLogStatsHelper.globalTopData(startDate, now, topParamList);
            }
        });
    }


    /**
     * 今日统计信息。
     *
     * @return
     */
    public static ResponseData<AccessGlobalStats> todayGlobalStats() {
        return FusionCache.get(GLOBAL_STATS_CACHE, "today");
    }

    /**
     * 最近一天分时指标。
     *
     * @return
     */
    public static ResponseData<Map<String, Map<String, Double>>> latestGlobalMetric() {
        return FusionCache.get(GLOBAL_METRICS_CACHE, "latest");
    }

    /**
     * 最近一天状态码统计。
     *
     * @return
     */
    public static ResponseData<Map<String, List<Map<String, Object>>>> latestGlobalCode() {
        return FusionCache.get(GLOBAL_CODE_CACHE, "latest");
    }

    /**
     * 最近一天排行统计。
     *
     * @return
     */
    public static ResponseData<Map<String, List<Map<String, Object>>>> latestGlobalTop() {
        return FusionCache.get(GLOBAL_TOP_CACHE, "latest");
    }


    /**
     * SAAS分组统计数据。
     *
     * @return
     */
    public static ResponseData<List<AccessSaasStats>> saasStats(Date startDate, Date endDate) {
        String dsl = String.format(SAAS_STATS_DSL, dateFormat.format(startDate), dateFormat.format(endDate));
        SearchResponse<String> statsResponse = dslQuery(esConfig.getServer(), esConfig.getUsername(), esConfig.getPassword(), String.class, GatewayCenterConstants.ACCESS_LOG_INDEX, dsl);
        if (statsResponse == null || statsResponse.getAggregations() == null) {
            return ResponseData.errorMsg("ES查询失败！");
        }
        var saasMap = convertAggBucketAggBucketFlatMap(statsResponse.getAggregations()).get("saasStats");
        if (saasMap == null) {
            return ResponseData.errorMsg("SaasStats聚合数据查询失败！");
        }
        List<AccessSaasStats> saasStatsList = new ArrayList<>();
        saasMap.forEach((saasId, saasStatsMap) -> {
            AccessSaasStats saasStats = JsonUtils.convert(saasStatsMap, AccessSaasStats.class);
            saasStats.setSaasId(Long.parseLong(saasId));
            saasStatsList.add(saasStats);
        });
        return ResponseData.success(saasStatsList);
    }

    /**
     * 全局统计数据。
     *
     * @return
     */
    public static ResponseData<AccessGlobalStats> globalStats(Date startDate, Date endDate) {
        String dsl = String.format(GLOBAL_STATS_DSL, dateFormat.format(startDate), dateFormat.format(endDate));
        SearchResponse<String> statsResponse = dslQuery(esConfig.getServer(), esConfig.getUsername(), esConfig.getPassword(), String.class, GatewayCenterConstants.ACCESS_LOG_INDEX, dsl);
        if (statsResponse == null || statsResponse.getAggregations() == null) {
            return ResponseData.errorMsg("ES查询失败！");
        }
        AccessGlobalStats globalStats = JsonUtils.convert(convertAggBucketFlatMap(statsResponse.getAggregations()), AccessGlobalStats.class);
        return ResponseData.success(globalStats);
    }

    /**
     * 全局统计metrics数据。
     *
     * @return
     */
    public static ResponseData<Map<String, Map<String, Double>>> globalMetrics(Date startDate, Date endDate, int intervalMinutes) {
        String dsl = String.format(GLOBAL_METRICS_DSL, dateFormat.format(startDate), dateFormat.format(endDate), intervalMinutes);
        SearchResponse<String> searchResponse = dslQuery(esConfig.getServer(), esConfig.getUsername(), esConfig.getPassword(), String.class, GatewayCenterConstants.ACCESS_LOG_INDEX, dsl);
        if (searchResponse == null || searchResponse.getAggregations() == null) {
            return ResponseData.errorMsg("ES查询失败！");
        }
        var histogramMap = convertAggBucketAggBucketFlatMap(searchResponse.getAggregations()).get("histogram");
        if (histogramMap == null) {
            return ResponseData.errorMsg("histogram数据查询失败！");
        }
        return ResponseData.success(histogramMap);
    }

    /**
     * 获取top数据。
     *
     * @return
     */
    public static ResponseData<Map<String, List<Map<String, Object>>>> globalTopData(Date startDate, Date endDate, List<TopParam> topParamList) {
        String topDsl = topParamList.stream().map(TopParam::genDsl).collect(Collectors.joining(",\n"));
        String dsl = String.format(GLOBAL_TOP_DSL, dateFormat.format(startDate), dateFormat.format(endDate), topDsl);
        SearchResponse<String> searchResponse = dslQuery(esConfig.getServer(), esConfig.getUsername(), esConfig.getPassword(), String.class, GatewayCenterConstants.ACCESS_LOG_INDEX, dsl);
        if (searchResponse == null || searchResponse.getAggregations() == null) {
            return ResponseData.errorMsg("ES查询失败！");
        }
        return ResponseData.success(convertAggBucketListMap(searchResponse.getAggregations()));
    }

    /**
     * 获取状态码数据。
     *
     * @return
     */
    public static ResponseData<Map<String, List<Map<String, Object>>>> globalCodeData(Date startDate, Date endDate) {
        String dsl = String.format(GLOBAL_CODE_DSL, dateFormat.format(startDate), dateFormat.format(endDate));
        SearchResponse<String> searchResponse = dslQuery(esConfig.getServer(), esConfig.getUsername(), esConfig.getPassword(), String.class, GatewayCenterConstants.ACCESS_LOG_INDEX, dsl);
        if (searchResponse == null || searchResponse.getAggregations() == null) {
            return ResponseData.errorMsg("ES查询失败！");
        }
        Map<String, List<Map<String, Object>>> map = new LinkedHashMap<>();
        var codeStats = searchResponse.getAggregations().get("codeStats");
        if (codeStats != null) {
            if (codeStats.getBuckets() != null) {
                codeStats.getBuckets().forEach(bucket -> {
                    String key = "topCode" + bucket.getKey();
                    if (bucket.getSubAggregations() != null) {
                        bucket.getSubAggregations().forEach((k1, v1) -> {
                            if (v1.getBuckets() != null) {
                                List<Map<String, Object>> list = new ArrayList<>();
                                v1.getBuckets().forEach(bucket2 -> {
                                    Map<String, Object> subMap = new LinkedHashMap<>();
                                    subMap.put("name", bucket2.getKey());
                                    subMap.put("count", bucket2.getDocCount());
                                    list.add(subMap);
                                });
                                map.put(key + k1, list);
                            }
                        });
                    }
                });
            }
        }
        return ResponseData.success(map);
    }


    /**
     * dsl查询日志
     *
     * @param tClass    日志对象类型
     * @param indexName 索引
     * @param dslQuery  dsl查询条件
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T> SearchResponse<T> dslQuery(String esHost, String esUser, String esPass, Class<T> tClass, String indexName, String dslQuery) {
        if (StringUtils.isBlank(esHost)) {
            return null;
        }
        StringBuilder urlBuilder = new StringBuilder(esHost);
        urlBuilder.append("/").append(indexName).append("/").append("_search");
        SearchResponse<T> resp = null;
        JavaType javaType = JsonUtils.constructParametricType(SearchResponse.class, tClass);

        try {
            Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.toString());
            if (StringUtils.isNotBlank(esUser) && StringUtils.isNotBlank(esPass)) {
                requestBuilder.header("Authorization", Credentials.basic(esUser, esPass));
            }
            resp = (SearchResponse<T>) HTTP_INTERFACE.requestForEntity(requestBuilder.post(RequestBody.create(dslQuery, MediaTypes.JSON_UTF8)).build(), javaType).getValue();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resp;
    }


//    public static void main(String[] args) {
//        AccessLogStatsHelper.esConfig = new GatewayCenterProperties.EsConfig();
//        AccessLogStatsHelper.esConfig.setServer("http://192.168.88.21:9200");
//        AccessLogStatsHelper.esConfig.setUsername("elastic");
//        AccessLogStatsHelper.esConfig.setPassword("espasswd");
//        Date now = SystemClock.nowDate();
//        Date startDate = DateUtils.beginOfMonth(now);
//        Date endDate = DateUtils.endOfYesterday(now);
//        var globalStats = AccessLogStatsHelper.globalStats(startDate, endDate);
//        System.out.println(globalStats);
//        var saasStats = AccessLogStatsHelper.saasStats(startDate, endDate);
//        System.out.println(saasStats);
//        var globalMetrics = AccessLogStatsHelper.globalMetrics(startDate, endDate, 5);
//        System.out.println(globalMetrics);
//        List<AccessLogStatsHelper.TopParam> topParamList = new ArrayList<>();
//        topParamList.add(new AccessLogStatsHelper.TopParam("topSaas", "saasId", "_count", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topSaasByTime", "saasId", "responseMillis", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topSaasBySize", "saasId", "responseSize", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topUser", "userId", "_count", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topUserByTime", "userId", "responseMillis", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topUserBySize", "userId", "responseSize", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topIp", "userIp.keyword", "_count", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topIpByTime", "userIp.keyword", "responseMillis", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topIpBySize", "userIp.keyword", "responseSize", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topService", "serviceName.keyword", "_count", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topServiceByTime", "serviceName.keyword", "responseMillis", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topServiceBySize", "serviceName.keyword", "responseSize", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topApi", "requestPath.keyword", "_count", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topApiByTime", "requestPath.keyword", "responseMillis", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topApiBySize", "requestPath.keyword", "responseSize", 100, 10));
//        topParamList.add(new AccessLogStatsHelper.TopParam("topCode", "responseCode", "_count", 100, 10));
//        var topDataMap = AccessLogStatsHelper.globalTopData(startDate, now, topParamList);
//        System.out.println(topDataMap);
//        var codeData = AccessLogStatsHelper.globalCodeData(startDate, endDate);
//        System.out.println(codeData);
//    }


    /**
     * 获取聚合值。
     */
    private static double getAggValue(Map<String, SearchResponse.Aggregation> aggMap, String aggName) {
        if (aggMap == null) return 0d;
        SearchResponse.Aggregation aggregation = aggMap.get(aggName);
        if (aggregation == null) return 0d;
        return aggregation.getValue();
    }

    /**
     * 获取聚合值。
     * 聚合值下方的Bucket，则放入到子map中。
     * 结构为agg->bucket。
     */
    private static Map<String, List<Map<String, Object>>> convertAggBucketListMap(Map<String, SearchResponse.Aggregation> aggMap) {
        Map<String, List<Map<String, Object>>> map = new LinkedHashMap<>();
        if (aggMap == null) return map;
        aggMap.forEach((k, v) -> {
            List<Map<String, Object>> list = new ArrayList<>();
            if (v.getBuckets() != null) {
                v.getBuckets().forEach(bucket -> {
                    Map<String, Object> subMap = new LinkedHashMap<>();
                    subMap.put("name", bucket.getKey());
                    subMap.put("count", bucket.getDocCount());
                    if (bucket.getSubAggregations() != null) {
                        bucket.getSubAggregations().forEach((k1, v1) -> {
                            subMap.put(k1, v1.getValue());
                        });
                    }
                    list.add(subMap);
                });
            }
            map.put(k, list);
        });
        return map;
    }

    /**
     * 获取聚合值。
     * 聚合值中如果有bucket，则将bucket中的值拉平加入到map中。
     * 结构为agg->bucket->agg+bucket。
     */
    private static Map<String, Map<String, Map<String, Double>>> convertAggBucketAggBucketFlatMap(Map<String, SearchResponse.Aggregation> aggMap) {
        Map<String, Map<String, Map<String, Double>>> map = new LinkedHashMap<>();
        if (aggMap == null) return map;
        aggMap.forEach((k, v) -> {
            if (v.getBuckets() != null) {
                Map<String, Map<String, Double>> subMap = new LinkedHashMap<>();
                v.getBuckets().forEach(bucket -> {
                    if (bucket.getSubAggregations() != null) {
                        Map<String, Double> subSubMap = new LinkedHashMap<>();
                        bucket.getSubAggregations().forEach((k1, v1) -> {
                            if (v1.getBuckets() != null) {
                                v1.getBuckets().forEach(bucket2 -> {
                                    subSubMap.put(k1 + bucket2.getKey(), (double) bucket2.getDocCount());
                                });
                            } else {
                                subSubMap.put(k1, v1.getValue());
                            }
                        });
                        subMap.put(bucket.getKey(), subSubMap);
                    }
                });
                map.put(k, subMap);
            }
        });
        return map;
    }

    /**
     * 获取聚合值。
     * 聚合值中如果有bucket，则将bucket中的值拉平加入到map中。
     * 结构为agg+bucket。
     */
    private static Map<String, Double> convertAggBucketFlatMap(Map<String, SearchResponse.Aggregation> aggMap) {
        Map<String, Double> map = new LinkedHashMap<>();
        if (aggMap == null) return map;
        aggMap.forEach((k, v) -> {
            if (v.getBuckets() != null) {
                v.getBuckets().forEach(bucket -> {
                    map.put(k + bucket.getKey(), (double) bucket.getDocCount());
                });
            } else {
                map.put(k, v.getValue());
            }
        });
        return map;
    }

    public static class TopParam {

        public static final String RANK_REQUEST_COUNT = "_count";

        public static final String RANK_RESPONSE_MILLISECONDS = "responseMillis";

        public static final String RANK_RESPONSE_SIZE = "responseSize";

        public static final String AGG_SAAS_ID = "saasId";

        public static final String AGG_USER_ID = "userId";

        public static final String AGG_USER_IP = "userIp.keyword";

        public static final String AGG_SERVICE_NAME = "serviceName.keyword";

        public static final String AGG_REQUEST_PATH = "requestPath.keyword";

        public static final String AGG_RESPONSE_CODE = "responseCode";

        /**
         * 排行榜名。
         */
        private String topName;
        /**
         * 聚合参数。
         */
        private String aggParam;
        /**
         * 排序参数。
         */
        private String rankParam;
        /**
         * 排行榜数量。
         */
        private int topNum;
        /**
         * 最小值。
         */
        private int minValue;

        public TopParam(String topName, String aggParam, String rankParam, int topNum, int minValue) {
            this.topName = topName;
            this.aggParam = aggParam;
            this.rankParam = rankParam;
            this.topNum = topNum;
            this.minValue = minValue;
        }

        public TopParam() {
        }

        /**
         * 生成DSL.
         *
         * @return
         */
        public String genDsl() {
            String dsl = "\"" + topName + "\": {\n" + "  \"terms\": {\n" + "    \"field\": \"" + aggParam + "\",\n" + "    \"size\": " + topNum + ",\n" + "    \"order\": {\n" + "      \"" + rankParam + "\": \"desc\"\n" + "    },\n" + "    \"min_doc_count\": " + minValue + "\n" + "  }";
            if (rankParam.equals(RANK_REQUEST_COUNT)) {
                dsl += "\n}";
            } else {
                dsl += ",\n" + "  \"aggs\": {\n" + "    \"" + rankParam + "\": {\n" + "      \"sum\": {\n" + "        \"field\": \"" + rankParam + "\"\n" + "      }\n" + "    }\n" + "  }\n" + "}\n";
            }
            return dsl;
        }

    }


}