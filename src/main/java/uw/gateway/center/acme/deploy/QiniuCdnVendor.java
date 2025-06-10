package uw.gateway.center.acme.deploy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
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
import uw.httpclient.util.MediaTypes;
import uw.httpclient.util.SSLContextUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 七牛cdn供应商。
 */
public class QiniuCdnVendor implements DeployVendor {

    /**
     * HTTP客户端
     */
    private static final HttpInterface CDN_CLIENT = new JsonInterfaceHelper(HttpConfig.builder().connectTimeout(30000).readTimeout(30000).writeTimeout(30000).retryOnConnectionFailure(true).trustManager( SSLContextUtils.getTrustAllManager() ).sslSocketFactory( SSLContextUtils.getTruestAllSocketFactory()).hostnameVerifier((hostName, sslSession) -> true).build());

    /**
     * API协议
     */
    private static final String API_PROTOCOL = "https://";

    /**
     * API主机
     */
    private static final String API_HOST = "api.qiniu.com";

    /**
     * 添加SSL证书的URL
     */
    private static final String ADD_CERT_API = "/sslcert";

    /**
     * 绑定SSL证书的URL
     */
    private static final String BIND_CERT_API = "/domain/{domain}/sslize";


    private String apiKey;

    private String apiSecret;

    private String domain;

    private QiniuCdnVendor(String apiKey, String apiSecret, String domain) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.domain = domain;
    }

    public QiniuCdnVendor() {
    }

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "七牛云CDN";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "七牛云CDN";
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
        String apiKey = config.getParam(Param.API_KEY);
        String apiSecret = config.getParam(Param.API_SECRET);
        String domain = config.getParam(Param.DOMAIN);
        return new QiniuCdnVendor(apiKey, apiSecret, domain);
    }

    /**
     * 添加SSL证书。
     *
     * @param certName 证书名称
     * @param certKey  证书私钥
     * @param certData 证书内容
     * @return 记录ID或其他标识
     */
    @Override
    public ResponseData<String> deployCert(String certName, String certKey, String certData) {
        Map<String, String> certBody = new LinkedHashMap<>();
        certBody.put("name", certName);
        certBody.put("pri", certKey);
        certBody.put("ca", certData);

        String certBodyJson = JsonUtils.toString(certBody);
        // 上传SSL证书
        HttpData addCertData = CDN_CLIENT.postBodyForData(API_PROTOCOL + API_HOST + ADD_CERT_API, Map.of("Authorization", generateToken("POST", ADD_CERT_API, certBodyJson), "Content-Type", String.valueOf(MediaTypes.JSON_UTF8), "Host", API_HOST), certBodyJson);

        if (addCertData.getStatusCode() != 200) {
            return ResponseData.errorMsg("Failed to upload SSL certificate: " + addCertData.getResponseData());
        }
        Map<String, String> resultMap = JsonUtils.parse(addCertData.getResponseData(), new TypeReference<Map<String, String>>() {
        });
        String certId = resultMap.get("certID");
        if (StringUtils.isBlank(certId)) {
            return ResponseData.warnMsg("Failed to parse cert ID from response: " + addCertData.getResponseData());
        }
        //  绑定SSL证书
        Map<String, Object> bindBody = new LinkedHashMap<>();
        bindBody.put("certID", certId);
        bindBody.put("forceHttps", false);
        bindBody.put("http2Enable", true);
        String bindBodyJson = JsonUtils.toString(bindBody);
        String bindCertApi = BIND_CERT_API.replace("{domain}", domain);
        HttpData bindCertData = CDN_CLIENT.putBodyForData(API_PROTOCOL + API_HOST + bindCertApi, Map.of("Authorization", generateToken("PUT", bindCertApi, bindBodyJson), "Content-Type", String.valueOf(MediaTypes.JSON_UTF8), "Host", API_HOST), bindBodyJson);
        if (bindCertData.getStatusCode() != 200) {
            return ResponseData.errorMsg("Failed to bind SSL certificate: " + bindCertData.getResponseData());
        }
        return ResponseData.success(certId);
    }

    /**
     * 生成访问令牌
     */
    private String generateToken(String method, String apiPath, String requestBody) {
        try {
            StringBuilder signingSb = new StringBuilder();
            signingSb.append(method.toUpperCase()).append(" ").append(apiPath).append("\n");
            signingSb.append("Host: ").append(API_HOST).append("\n");
            signingSb.append("Content-Type: ").append(String.valueOf(MediaTypes.JSON_UTF8)).append("\n");
            signingSb.append("\n");
            if (requestBody != null) {
                signingSb.append(requestBody);
            }
            System.out.println(signingSb);
            // 构建待签名的原始字符串，不包含 API 主机名
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            byte[] sign = mac.doFinal(signingSb.toString().getBytes(StandardCharsets.UTF_8));
            return "Qiniu " + apiKey + ":" + Base64.getUrlEncoder().encodeToString(sign);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate access token", e);
        }
    }


    /**
     * Vendor参数定义。
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "Vendor参数", description = "七牛CDN Vendor参数")
    public enum Param implements JsonConfigParam {
        API_KEY(JsonConfigParam.ParamType.STRING, "", "API Key", "API Key", ""), API_SECRET(JsonConfigParam.ParamType.STRING, "", "API Secret", "API Secret", ""), DOMAIN(JsonConfigParam.ParamType.STRING, "", "Domain", "主机域名", "");

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
