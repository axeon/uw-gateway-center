package uw.gateway.center.acme.dns;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.vo.JsonConfigBox;
import uw.common.app.vo.JsonConfigParam;
import uw.common.dto.ResponseData;
import uw.common.util.EnumUtils;
import uw.gateway.center.acme.DnsVendor;
import uw.httpclient.http.HttpConfig;
import uw.httpclient.http.HttpData;
import uw.httpclient.http.HttpInterface;
import uw.httpclient.json.JsonInterfaceHelper;
import uw.httpclient.util.SSLContextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cloudflare DNS API 实现
 */
public class CloudflareDnsVendor implements DnsVendor {
    private static final HttpInterface DNS_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory()).hostnameVerifier((hostName, sslSession) -> true).build());

    private static final String API_URL = "https://api.cloudflare.com/client/v4";
    private String apiToken;
    private String email;

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "CloudFlareDNS(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "CloudFlareDNS";
    }

    /**
     * 供应商版本
     */
    @Override
    public String vendorVersion() {
        return "1.0.0";
    }

    /**
     * 供应商图标
     */
    @Override
    public String vendorIcon() {
        return "";
    }

    /**
     * Vendor参数信息集合，管理员可见。
     */
    @Override
    public List<JsonConfigParam> vendorParam() {
        return List.of();
    }

    @Override
    public DnsVendor build(JsonConfigBox config) {
        this.apiToken = config.getParam("CF_Token");
        this.email = config.getParam("CF_Email");
        return null;

    }

    @Override
    public ResponseData<String> addDnsRecord(String domain, String recordType, String recordName, String recordValue) {
        // 首先需要获取zone ID
        String zoneId = getZoneId(domain).getData();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiToken);
        headers.put("Content-Type", "application/json");

        Map<String, String> data = new HashMap<>();
        data.put("type", recordType);
        data.put("name", "_acme-challenge." + domain);
        data.put("content", recordName);
        data.put("ttl", "120");


        HttpData response = DNS_CLIENT.postFormForData(API_URL + "/zones/" + zoneId + "/dns_records", headers, data);

        if (response.getStatusCode() != 200) {
            return ResponseData.warnMsg("Failed to add DNS record: " + response.getResponseData());
        }

        // 解析响应获取记录ID
        return ResponseData.success(response.getResponseData()); // 需要从JSON响应中解析实际的记录ID
    }

    @Override
    public ResponseData<String> removeDnsRecord(String domain, String recordId) {
        String zoneId = getZoneId(domain).getData();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiToken);


        HttpData response = DNS_CLIENT.deleteForData(API_URL + "/zones/" + zoneId + "/dns_records/" + recordId, headers);

        return response.getStatusCode() == 200 ? ResponseData.success(response.getResponseData()) : ResponseData.errorMsg("Failed to remove DNS record: " + response.getResponseData());
    }

    private ResponseData<String> getZoneId(String domain) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiToken);

        Map<String, String> params = new HashMap<>();
        params.put("name", domain);


        HttpData response = DNS_CLIENT.getForData(API_URL + "/zones", headers, params);

        if (response.getStatusCode() != 200) {
            return ResponseData.warnMsg("Failed to get zone ID for domain: " + domain);
        }

        // 从响应中解析zone ID
        return ResponseData.success(response.getResponseData()); // 需要从JSON响应中解析实际的zone ID
    }

    /**
     * vendor参数
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "vendor参数", description = "vendor参数")
    public enum Param implements JsonConfigParam {
        API_KEY(JsonConfigParam.ParamType.STRING, "", "API Key", "API Key", ""),
        API_SECRET(JsonConfigParam.ParamType.STRING, "", "API密钥", "API密钥", "");

        private final JsonConfigParam.ParamData paramData;

        Param(JsonConfigParam.ParamType type, String value, String name, String desc, String regex) {
            this.paramData = new JsonConfigParam.ParamData(EnumUtils.enumNameToDotCase(name()), type, value, name, desc, regex);
        }

        /**
         * 配置参数数据。
         *
         * @return
         */
        @Override
        public ParamData getParamData() {
            return paramData;
        }
    }
}