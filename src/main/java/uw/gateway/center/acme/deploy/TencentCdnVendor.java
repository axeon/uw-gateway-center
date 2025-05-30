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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 腾讯云cdn供应商。
 */
public class TencentCdnVendor implements DeployVendor {
    /**
     * HTTP客户端
     */
    private static final HttpInterface CDN_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).hostnameVerifier((hostName, sslSession) -> true).build());
    /**
     * API地址
     */
    private static final String API_URL = "https://cdn.tencentcloudapi.com";
    private String secretId;
    private String secretKey;
    private String region;

    private TencentCdnVendor(String secretId, String secretKey, String region) {
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.region = region;
    }

    public TencentCdnVendor() {
    }

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "腾讯云CDN(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "腾讯云CDN";
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
        String secretId = config.getParam(Param.API_KEY);
        String secretKey = config.getParam(Param.API_SECRET);
        String region = config.getParam(Param.REGION);
        return new TencentCdnVendor(secretId, secretKey, region);
    }

    /**
     * 添加ssl证书。
     *
     * @return 记录ID或其他标识
     */
    @Override
    public ResponseData<String> deployCert(String certName, String certKey, String certData) {
        try {
            // 构建请求参数
            Map<String, Object> params = new HashMap<>();
            params.put("Action", "DescribeEcdnHttpsCertificates");
            params.put("Version", "2019-10-10");
            params.put("Region", "ap-beijing");

            // 获取现有证书列表
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            params.put("Timestamp", timestamp);
            params.put("Nonce", (int) (Math.random() * 100000));
            params.put("SecretId", secretId);

            // 生成签名
            String signature = signRequest(params, "GET", secretKey);
            params.put("Signature", signature);

            HttpData listResponse = CDN_CLIENT.getForData(API_URL + "?" + buildQueryString(params));

            if (listResponse.getStatusCode() != 200) {
                return ResponseData.errorMsg("Failed to get certificate list: " + listResponse.getResponseData());
            }

            Map<String, Object> listResult = JsonUtils.parse(listResponse.getResponseData(), new TypeReference<Map<String, Object>>() {
            });
            List<Map<String, Object>> certificates = (List<Map<String, Object>>) ((Map<String, Object>) listResult.get("Response")).get("CertificateList");

            // 检查是否已存在相同域名的证书
            String certId = null;
            for (Map<String, Object> cert : certificates) {
                if (cert.get("SubjectCN").equals(certName)) {
                    certId = (String) cert.get("CertificateId");
                    break;
                }
            }

            // 上传新证书
            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("Action", "UploadEcdnHttpsCertificate");
            uploadParams.put("Version", "2019-10-10");
            uploadParams.put("Region", "ap-beijing");
            uploadParams.put("CertificateName", certName);
            uploadParams.put("CertificateType", "custom");
            uploadParams.put("Certificate", certData);
            uploadParams.put("PrivateKey", certKey);

            timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            uploadParams.put("Timestamp", timestamp);
            uploadParams.put("Nonce", (int) (Math.random() * 100000));
            uploadParams.put("SecretId", secretId);

            // 生成签名
            String uploadSignature = signRequest(uploadParams, "POST", secretKey);
            uploadParams.put("Signature", uploadSignature);

            HttpData uploadResponse = CDN_CLIENT.postBodyForData(API_URL, Map.of("Content-Type", "application/x-www-form-urlencoded"), buildQueryString(uploadParams));

            if (uploadResponse.getStatusCode() == 200) {
                Map<String, Object> uploadResult = JsonUtils.parse(uploadResponse.getResponseData(), new TypeReference<Map<String, Object>>() {
                });
                String newCertId = (String) ((Map<String, Object>) uploadResult.get("Response")).get("CertificateId");
                return ResponseData.success(newCertId);
            } else {
                return ResponseData.errorMsg("Failed to upload SSL certificate: " + uploadResponse.getResponseData());
            }
        } catch (Exception e) {
            return ResponseData.errorMsg("Error uploading SSL certificate: " + e.getMessage());
        }
    }

    /**
     * 生成签名
     */
    private String signRequest(Map<String, Object> params, String httpMethod, String secretKey) throws Exception {
        // 按照参数名称顺序排列参数
        List<String> sortedParams = new ArrayList<>(params.keySet());
        Collections.sort(sortedParams);

        // 构造待签名字符串
        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (String param : sortedParams) {
            if (param.equals("Signature")) continue;
            canonicalizedQueryString.append(param).append("=").append(params.get(param)).append("&");
        }

        String stringToSign = httpMethod + API_URL.replace("https://", "") + "?" + canonicalizedQueryString.toString().substring(0, canonicalizedQueryString.length() - 1);

        // 使用HMAC-SHA1进行签名
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
        byte[] signatureBytes = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));

        // Base64编码
        return Base64.getEncoder().encodeToString(signatureBytes);
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
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return sb.toString();
    }


    /**
     * Vendor参数定义。
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "Vendor参数", description = "Vendor参数")
    public enum Param implements JsonConfigParam {
        API_KEY(JsonConfigParam.ParamType.STRING, "", "API Key", "API Key", ""),
        API_SECRET(JsonConfigParam.ParamType.STRING, "", "API Secret", "API Secret", ""),
        REGION(JsonConfigParam.ParamType.STRING, "", "Region", "Region", "");
        private final JsonConfigParam.ParamData paramData;

        Param(JsonConfigParam.ParamType type, String value, String name, String desc, String regex) {
            this.paramData = new JsonConfigParam.ParamData(EnumUtils.enumNameToDotCase(name()), type, value, name, desc, regex);
        }

        @Override
        public ParamData getParamData() {
            return paramData;
        }
    }
}
