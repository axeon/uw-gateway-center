package uw.gateway.center.acme.deploy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.codec.binary.Hex;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * aws cdn供应商。
 */
public class AwsCdnVendor implements DeployVendor {
    /**
     * HTTP客户端
     */
    private static final HttpInterface CDN_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory()).hostnameVerifier((hostName, sslSession) -> true).build());
    /**
     * API地址
     */
    private static final String API_URL = "https://cloudfront.amazonaws.com/2020-05-31/distribution";
    private String accessKeyId;
    private String secretAccessKey;
    private String region;

    private AwsCdnVendor(String accessKeyId, String secretAccessKey, String region) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.region = region;
    }

    public AwsCdnVendor() {
    }

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "AWS CDN(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "AWS CDN";
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
        String secretAccessKey = config.getParam(Param.API_SECRET);
        String region = config.getParam(Param.REGION);
        return new AwsCdnVendor(accessKeyId, secretAccessKey, region);
    }

    /**
     * 添加ssl证书。
     *
     * @return 记录ID或其他标识
     */
    @Override
    public ResponseData<String> deployCert(String certName, String certKey, String certData) {
        try {
            // 构建AWS签名头
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", generateAwsAuthHeader("POST", "/2020-05-31/distribution", Collections.emptyMap(), certData));
            headers.put("Content-Type", "application/json");

            // 构建SSL证书配置
            Map<String, Object> certConfig = new HashMap<>();
            certConfig.put("Certificate", certData);
            certConfig.put("PrivateKey", certKey);
            certConfig.put("CertificateName", certName);

            // 发送请求
            HttpData httpData = CDN_CLIENT.postBodyForData(API_URL, headers, certConfig);

            if (httpData.getStatusCode() == 201) {
                Map<String, Object> result = JsonUtils.parse(httpData.getResponseData(), new TypeReference<Map<String, Object>>() {
                });
                Map<String, Object> distribution = (Map<String, Object>) result.get("Distribution");
                Map<String, Object> distributionConfig = (Map<String, Object>) distribution.get("DistributionConfig");
                Map<String, Object> viewerCertificate = (Map<String, Object>) distributionConfig.get("ViewerCertificate");

                String certificateId = (String) viewerCertificate.get("IAMCertificateId");
                return ResponseData.success(certificateId);
            } else {
                return ResponseData.errorMsg("Failed to upload SSL certificate: " + httpData.getResponseData());
            }
        } catch (Exception e) {
            return ResponseData.errorMsg("Error uploading SSL certificate: " + e.getMessage());
        }
    }

    /**
     * 生成AWS认证头
     */
    private String generateAwsAuthHeader(String httpMethod, String resourcePath, Map<String, String> queryParams, String body) throws Exception {
        String amzDate = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.BASIC_ISO_DATE);
        String dateStamp = amzDate.substring(0, 8);

        // 创建标准化请求
        StringBuilder canonicalRequest = new StringBuilder();
        canonicalRequest.append(httpMethod).append("\n").append(resourcePath).append("\n\n");

        // 添加头部
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        headers.put("host", "cloudfront.amazonaws.com");
        headers.put("x-amz-date", amzDate);

        // 添加签名头部
        canonicalRequest.append("host;x-amz-date\n").append("\n").append("AWS4-HMAC-SHA256\n").append(amzDate).append("\n").append(dateStamp).append("/us-east-1/cloudfront/aws4_request\n").append(Hex.encodeHexString(MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8))));

        // 创建字符串到签名
        String stringToSign = canonicalRequest.toString();

        // 计算签名
        byte[] signingKey = getSignatureKey(secretAccessKey, dateStamp, "us-east-1", "cloudfront");
        byte[] signatureBytes = Mac.getInstance("HmacSHA256").doFinal();
        String signature = Hex.encodeHexString(signatureBytes);

        return String.format("AWS4-HMAC-SHA256 Credential=%s/%s/us-east-1/cloudfront/aws4_request, SignedHeaders=host;x-amz-date, Signature=%s", accessKeyId, dateStamp, signature);
    }

    /**
     * 生成AWS签名密钥
     */
    private byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes(StandardCharsets.UTF_8);
        byte[] kDate = HmacSHA256(dateStamp, kSecret);
        byte[] kRegion = HmacSHA256(regionName, kDate);
        byte[] kService = HmacSHA256(serviceName, kRegion);
        return HmacSHA256("aws4_request", kService);
    }

    /**
     * HMAC-SHA256加密
     */
    private byte[] HmacSHA256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Vendor参数定义。
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "Vendor参数", description = "AWS CDN Vendor参数")
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
