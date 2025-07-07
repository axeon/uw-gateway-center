package uw.gateway.center.acme.deploy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.vo.JsonConfigBox;
import uw.common.app.vo.JsonConfigParam;
import uw.common.dto.ResponseData;
import uw.common.util.EnumUtils;
import uw.common.util.JsonUtils;
import uw.gateway.center.acme.DeployVendor;
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
 * 阿里云cdn供应商。
 */
public class AliCdnVendor implements DeployVendor {
    /**
     * HTTP客户端
     */
    private static final HttpInterface CDN_CLIENT = new JsonInterfaceHelper(HttpConfig.builder()
            .connectTimeout(30000)
            .readTimeout(30000)
            .writeTimeout(30000)
            .retryOnConnectionFailure(true)
            .trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory())
            .hostnameVerifier((hostName, sslSession) -> true)
            .build());
    /**
     * API地址
     */
    private static final String API_URL = "https://cdn.aliyuncs.com";
    private String accessKeyId;
    private String accessKeySecret;
    private String region;

    private AliCdnVendor(String accessKeyId, String accessKeySecret, String region) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.region = region;
    }

    public AliCdnVendor() {
    }

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "阿里云CDN(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "阿里云CDN";
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
    public DeployVendor build(JsonConfigBox config) {
        String accessKeyId = config.getParam(Param.API_KEY);
        String accessKeySecret = config.getParam(Param.API_SECRET);
        String region = config.getParam(Param.REGION);
        return new AliCdnVendor(accessKeyId, accessKeySecret, region);
    }

    /**
     * 添加ssl证书。
     *
     * @return 记录ID或其他标识
     */
    @Override
    public ResponseData<String> deployCert(String certName, String certKey, String certData) {
        Map<String, Object> params = new HashMap<>();
        params.put("Action", "UploadServerCertificate");
        params.put("RegionId", "cn-hangzhou");
        params.put("ServerCertificate", certName);
        params.put("PrivateKey", certKey);
        params.put("CertificateBody", certData);

        // 签名计算
        String timestamp = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
        params.put("Version", "2018-05-10");
        params.put("AccessKeyId", accessKeyId);
        params.put("SignatureVersion", "1.0");
        params.put("SignatureMethod", "HMAC-SHA1");
        params.put("Timestamp", timestamp);

        try {
            String signature = signRequest(params, "GET", accessKeySecret);
            params.put("Signature", signature);

            HttpData httpData = CDN_CLIENT.getForData(API_URL + "?" + buildQueryString(params));

            if (httpData.getStatusCode() == 200) {
                Map<String, Object> result = JsonUtils.parse(httpData.getResponseData(), new TypeReference<Map<String, Object>>() {
                });
                String certId = (String) result.get("ServerCertificateId");
                return ResponseData.success(certId);
            } else {
                return ResponseData.errorMsg("Failed to upload SSL certificate: " + httpData.getResponseData());
            }
        } catch (Exception e) {
            return ResponseData.errorMsg("Error uploading SSL certificate: " + e.getMessage());
        }
    }

    /**
     * 生成签名
     */
    private String signRequest(Map<String, Object> parameters, String httpMethod, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        // 按照参数名称顺序排列参数
        List<String> sortedParams = new ArrayList<>(parameters.keySet());
        Collections.sort(sortedParams);

        // 构造待签名字符串
        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (String param : sortedParams) {
            if (param.equals("Signature")) continue;
            canonicalizedQueryString.append("&").append(param).append("=").append(percentEncode(parameters.get(param).toString()));
        }

        String stringToSign = httpMethod + "&%2F&" + percentEncode(canonicalizedQueryString.toString().substring(1));

        // 使用HMAC-SHA1进行签名
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
        byte[] signatureBytes = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));

        // Base64编码
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    /**
     * URL编码
     */
    private String percentEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20")
                .replace("*", "%2A").replace("%7E", "~");
    }

    /**
     * 构建查询字符串
     */
    private String buildQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(percentEncode(entry.getValue().toString()));
        }
        return sb.toString();
    }

    /**
     * Vendor参数定义。
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "Vendor参数", description = "阿里云CDN Vendor参数")
    public enum Param implements JsonConfigParam {
        API_KEY(JsonConfigParam.ParamType.STRING, "", "API Key", ""),
        API_SECRET(JsonConfigParam.ParamType.STRING, "", "API Secret", ""),
        REGION(JsonConfigParam.ParamType.STRING, "", "Region", "");

        private final JsonConfigParam.ParamData paramData;

        Param(ParamType type, String value, String desc, String regex) {
            this.paramData = new JsonConfigParam.ParamData(EnumUtils.enumNameToDotCase(name()), type, value, desc, regex);
        }

        @Override
        public ParamData getParamData() {
            return paramData;
        }
    }
}
