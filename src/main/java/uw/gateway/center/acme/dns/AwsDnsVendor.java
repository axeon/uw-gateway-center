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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AWS Route 53 DNS API
 */
public class AwsDnsVendor implements DnsVendor {
    private static final HttpInterface DNS_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory()).hostnameVerifier((hostName, sslSession) -> true).build());

    private static final String API_URL = "https://route53.amazonaws.com/2013-04-01";
    private String accessKey;
    private String secretKey;

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "AWS DNS(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "AWS DNS";
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
        this.accessKey = config.getParam("AWS_ACCESS_KEY_ID");
        this.secretKey = config.getParam("AWS_SECRET_ACCESS_KEY");
        return null;

    }

    @Override
    public ResponseData<String> addDnsRecord(String domain, String recordType, String recordName, String recordValue) {
        String hostedZoneId = getHostedZoneId(domain).getData();

        String changeBatch = String.format("""
                {
                    "Changes": [{
                        "Action": "UPSERT",
                        "ResourceRecordSet": {
                            "Name": "_acme-challenge.%s",
                            "Type": "%s",
                            "TTL": 300,
                            "ResourceRecords": [{"Value": "\"%s\""}]
                        }
                    }]
                }""", domain, recordType, recordName);

        Map<String, String> headers = createAwsHeaders("POST", "/2013-04-01/hostedzone/" + hostedZoneId + "/rrset");


        HttpData response = DNS_CLIENT.postBodyForData(
                API_URL + "/hostedzone/" + hostedZoneId + "/rrset",
                headers,
                changeBatch
        );

        if (response.getStatusCode() != 200) {
            return ResponseData.warnMsg("Failed to add DNS record: " + response.getResponseData());
        }

        return ResponseData.success(hostedZoneId + "|" + "_acme-challenge." + domain);
    }

    @Override
    public ResponseData<String> removeDnsRecord(String domain, String recordId) {
        String[] parts = recordId.split("\\|");
        String hostedZoneId = parts[0];
        String recordName = parts[1];

        String changeBatch = String.format("""
                {
                    "Changes": [{
                        "Action": "DELETE",
                        "ResourceRecordSet": {
                            "Name": "%s",
                            "Type": "TXT",
                            "TTL": 300,
                            "ResourceRecords": [{"Value": ""}]
                        }
                    }]
                }""", recordName);

        Map<String, String> headers = createAwsHeaders("POST", "/2013-04-01/hostedzone/" + hostedZoneId + "/rrset");


        HttpData response = DNS_CLIENT.postBodyForData(
                API_URL + "/hostedzone/" + hostedZoneId + "/rrset",
                headers,
                changeBatch
        );

        return response.getStatusCode() == 200 ? ResponseData.success(response.getResponseData()) : ResponseData.errorMsg("Failed to remove DNS record: " + response.getResponseData());
    }

    private ResponseData<String> getHostedZoneId(String domain) {
        Map<String, String> headers = createAwsHeaders("GET", "/2013-04-01/hostedzone");


        HttpData response = DNS_CLIENT.getForData(API_URL + "/hostedzone", headers);

        if (response.getStatusCode() != 200) {
            return ResponseData.warnMsg("Failed to get hosted zone ID");
        }

        // 需要从响应中解析hosted zone ID
        return ResponseData.success(response.getResponseData()); // 实际实现需要从XML响应中解析
    }

    private Map<String, String> createAwsHeaders(String method, String path) {
        try {
            String timestamp = ZonedDateTime.now(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
            String date = timestamp.substring(0, 8);

            String stringToSign = String.format("AWS4-HMAC-SHA256\n%s\n%s/us-east-1/route53/aws4_request\n",
                    timestamp, date);

            // 计算签名
            byte[] kSecret = ("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8);
            byte[] kDate = hmacSHA256(date, kSecret);
            byte[] kRegion = hmacSHA256("us-east-1", kDate);
            byte[] kService = hmacSHA256("route53", kRegion);
            byte[] kSigning = hmacSHA256("aws4_request", kService);
            byte[] signature = hmacSHA256(stringToSign, kSigning);

            Map<String, String> headers = new HashMap<>();
            headers.put("X-Amz-Date", timestamp);
            headers.put("Authorization", String.format(
                    "AWS4-HMAC-SHA256 Credential=%s/%s/us-east-1/route53/aws4_request, SignedHeaders=host;x-amz-date, Signature=%s",
                    accessKey, date, bytesToHex(signature)
            ));
            headers.put("Content-Type", "application/xml");

            return headers;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create AWS headers", e);
        }
    }

    private byte[] hmacSHA256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
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