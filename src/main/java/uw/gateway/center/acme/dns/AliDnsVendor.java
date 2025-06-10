package uw.gateway.center.acme.dns;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import uw.common.app.vo.JsonConfigBox;
import uw.common.app.vo.JsonConfigParam;
import uw.common.dto.ResponseData;
import uw.common.util.EnumUtils;
import uw.common.util.JsonUtils;
import uw.gateway.center.acme.DnsVendor;
import uw.httpclient.http.HttpConfig;
import uw.httpclient.http.HttpData;
import uw.httpclient.http.HttpInterface;
import uw.httpclient.json.JsonInterfaceHelper;
import uw.httpclient.util.SSLContextUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 阿里云DNS API实现.
 */
public class AliDnsVendor implements DnsVendor {

    /**
     * DNS客户端
     */
    private static final HttpInterface DNS_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory()).hostnameVerifier((hostName, sslSession) -> true).build());

    /**
     * API地址
     */
    private static final String API_URL = "https://alidns.aliyuncs.com";

    /**
     * 访问密钥ID
     */
    private String apiKey;

    /**
     * 访问密钥密钥
     */
    private String apiSecret;

    /**
     * 构造函数。
     *
     * @param apiKey
     * @param apiSecret
     */
    private AliDnsVendor(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public AliDnsVendor() {
    }

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "阿里云DNS";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "阿里云DNS";
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
        return List.of(Param.values());
    }

    /**
     * 初始化API凭证
     *
     * @param config 特定的DNS服务商凭证
     * @return
     */
    @Override
    public DnsVendor build(JsonConfigBox config) {
        String apiKey = config.getParam(Param.API_KEY);
        String apiSecret = config.getParam(Param.API_SECRET);
        return new AliDnsVendor(apiKey, apiSecret);
    }

    @Override
    public ResponseData<String> addDnsRecord(String domain, String recordType, String recordName, String recordValue) {

        Map<String, String> params = new HashMap<>();
        params.put("Action", "AddDomainRecord");
        params.put("DomainName", domain);
        params.put("Type", recordType);
        params.put("RR", recordName);
        params.put("Value", recordValue);

        HttpData httpData = executeRequest(params);
        if (httpData.getStatusCode() != 200) {
            return ResponseData.errorMsg("Failed to add DNS record: " + httpData.getResponseData());
        }
        Map<String, String> resultMap = JsonUtils.parse(httpData.getResponseData(), new TypeReference<Map<String, String>>() {
        });
        String recordId = resultMap.get("RecordId");
        if (StringUtils.isNotBlank(recordId)) {
            return ResponseData.success(recordId);
        } else {
            return ResponseData.warnMsg("Failed to add DNS record: " + httpData.getResponseData());
        }
    }

    /**
     * 删除DNS记录
     *
     * @param domain   域名
     * @param recordId 记录ID
     * @return
     */
    @Override
    public ResponseData<String> removeDnsRecord(String domain, String recordId) {
        Map<String, String> params = new HashMap<>();
        params.put("Action", "DeleteDomainRecord");
        params.put("RecordId", recordId);
        HttpData httpData = executeRequest(params);
        if (httpData.getStatusCode() != 200) {
            return ResponseData.errorMsg("Failed to remove DNS record: " + httpData.getResponseData());
        }
        Map<String, String> resultMap = JsonUtils.parse(httpData.getResponseData(), new TypeReference<Map<String, String>>() {
        });
        if (StringUtils.equals(resultMap.get("RecordId"), recordId)) {
            return ResponseData.success(recordId);
        } else {
            return ResponseData.warnMsg("Failed to remove DNS record: " + httpData.getResponseData());
        }
    }

    /**
     * 执行请求.
     *
     * @param params
     * @return
     */
    private HttpData executeRequest(Map<String, String> params) {
        // 添加公共参数
        params.put("Format", "JSON");
        params.put("Version", "2015-01-09");
        params.put("AccessKeyId", apiKey);
        params.put("SignatureMethod", "HMAC-SHA1");
        params.put("Timestamp", getTimestamp());
        params.put("SignatureVersion", "1.0");
        params.put("SignatureNonce", String.valueOf(System.currentTimeMillis()));
        // 计算签名
        String signature = calculateSignature(params);
        params.put("Signature", signature);
        // 发送请求
        return DNS_CLIENT.postFormForData(API_URL, params);
    }

    /**
     * 计算签名
     *
     * @param params 参数
     * @return 签名
     */
    private String calculateSignature(Map<String, String> params) {
        try {
            // 按字典顺序排序参数
            TreeMap<String, String> sortedParams = new TreeMap<>(params);

            // 构建规范化请求字符串
            StringBuilder canonicalizedQueryString = new StringBuilder();
            for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
                canonicalizedQueryString.append("&").append(percentEncode(entry.getKey())).append("=").append(percentEncode(entry.getValue()));
            }

            // 构建字符串用于签名
            String stringToSign = "POST&%2F&" + percentEncode(canonicalizedQueryString.substring(1));

            // 计算HMAC值
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec((apiSecret + "&").getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(signData);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to calculate signature", e);
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    private String getTimestamp() {
        return ZonedDateTime.now(ZoneId.of("GMT")).format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * 编码字符串
     *
     * @param value 字符串
     * @return 编码后的字符串
     */
    private String percentEncode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (Exception e) {
            return value;
        }
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