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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

/**
 * Tencent DNS API 实现.
 */
public class TencentDnsVendor implements DnsVendor {
    private static final HttpInterface DNS_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory()).hostnameVerifier((hostName, sslSession) -> true).build());
    private static final String API_URL = "https://dnspod.tencentcloudapi.com";
    private String secretId;
    private String secretKey;

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "腾讯云DNS(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "腾讯云DNS";
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
        this.secretId = config.getParam("TENCENT_SecretId");
        this.secretKey = config.getParam("TENCENT_SecretKey");
        return null;

    }

    @Override
    public ResponseData<String> addDnsRecord(String domain, String recordType, String recordName, String recordValue) {
        Map<String, String> params = new HashMap<>();
        params.put("Domain", domain);
        params.put("SubDomain", "_acme-challenge");
        params.put("RecordType", recordType);
        params.put("RecordLine", "默认");
        params.put("Value", recordName);

        HttpData response = executeRequest("CreateRecord", params);
        if (response.getStatusCode() != 200) {
            return ResponseData.warnMsg("Failed to add DNS record: " + response.getResponseData());
        }

        return ResponseData.success(response.getResponseData()); // 需要从JSON响应中解析记录ID
    }

    @Override
    public ResponseData<String> removeDnsRecord(String domain, String recordId) {
        Map<String, String> params = new HashMap<>();
        params.put("Domain", domain);
        params.put("RecordId", recordId);

        HttpData response = executeRequest("DeleteRecord", params);
        return response.getStatusCode() == 200 ? ResponseData.success(response.getResponseData()) : ResponseData.errorMsg("Failed to remove DNS record: " + response.getResponseData());
    }

    private HttpData executeRequest(String action, Map<String, String> params) {
        try {
            String timestamp = String.valueOf(Instant.now().getEpochSecond());
            String nonce = String.valueOf(System.currentTimeMillis());

            // 构建规范请求串
            TreeMap<String, Object> sortedParams = new TreeMap<>(params);
            StringBuilder canonicalQueryString = new StringBuilder();
            for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
                canonicalQueryString.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }

            // 构建签名串
            String signStr = String.format("POST\ndnspod.tencentcloudapi.com\n/\n%s\n%s\n%s", canonicalQueryString.substring(1), timestamp, nonce);

            // 计算签名
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            String signature = Base64.getEncoder().encodeToString(mac.doFinal(signStr.getBytes(StandardCharsets.UTF_8)));

            // 设置请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("X-TC-Action", action);
            headers.put("X-TC-Version", "2021-03-23");
            headers.put("X-TC-Timestamp", timestamp);
            headers.put("X-TC-Region", "ap-guangzhou");
            headers.put("Authorization", buildAuthorization(signature, timestamp, nonce));
            headers.put("Content-Type", "application/json");

            // 发送请求

            return DNS_CLIENT.postFormForData(API_URL, headers, params);

        } catch (Exception e) {
            throw new RuntimeException("Failed to execute Tencent DNS API request", e);
        }
    }

    private String buildAuthorization(String signature, String timestamp, String nonce) {
        return String.format("TC3-HMAC-SHA256 Credential=%s/%s/dnspod/tc3_request, SignedHeaders=content-type;host, Signature=%s", secretId, timestamp.substring(0, 8), signature);
    }

    /**
     * vendor参数
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "vendor参数", description = "vendor参数")
    public enum Param implements JsonConfigParam {
        API_KEY(JsonConfigParam.ParamType.STRING, "", "API Key", ""),
        API_SECRET(JsonConfigParam.ParamType.STRING, "", "API密钥", "");

        private final JsonConfigParam.ParamData paramData;

        Param(ParamType type, String value, String desc, String regex) {
            this.paramData = new JsonConfigParam.ParamData(EnumUtils.enumNameToDotCase(name()), type, value, desc, regex);
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