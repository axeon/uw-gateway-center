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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cloudflare cdn供应商。
 */
public class CloudflareCdnVendor implements DeployVendor {
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
    private static final String API_URL = "https://api.cloudflare.com/client/v4/zones";
    private String apiKey;
    private String email;
    private String region;

    private CloudflareCdnVendor(String apiKey, String email, String region) {
        this.apiKey = apiKey;
        this.email = email;
        this.region = region;
    }

    public CloudflareCdnVendor() {
    }

    /**
     * 供应商名称
     */
    @Override
    public String vendorName() {
        return "CloudFlare CDN(TEST)";
    }

    /**
     * 供应商描述
     */
    @Override
    public String vendorDesc() {
        return "CloudFlare CDN";
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
        String email = config.getParam(Param.EMAIL);
        String region = config.getParam(Param.REGION);
        return new CloudflareCdnVendor(apiKey, email, region);
    }

    /**
     * 添加ssl证书。
     *
     * @return 记录ID或其他标识
     */
    @Override
    public ResponseData<String> deployCert(String certName, String certKey, String certData) {
        try {
            // 构建请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("X-Auth-Email", email);
            headers.put("X-Auth-Key", apiKey);
            headers.put("Content-Type", "application/json");

            // 获取zone ID
            HttpData zoneListResponse = CDN_CLIENT.getForData(API_URL, headers);
            if (zoneListResponse.getStatusCode() != 200 || !JsonUtils.parse(zoneListResponse.getResponseData(), new TypeReference<Map<String, Object>>() {
            }).get("success").equals(true)) {
                return ResponseData.errorMsg("Failed to get Cloudflare zones: " + zoneListResponse.getResponseData());
            }

            List<Map<String, Object>> zones = (List<Map<String, Object>>) JsonUtils.parse(zoneListResponse.getResponseData(), new TypeReference<Map<String, Object>>() {
            }).get("result");
            if (zones.isEmpty()) {
                return ResponseData.errorMsg("No zones found in Cloudflare account");
            }

            String zoneId = (String) zones.get(0).get("id");

            // 构建SSL证书请求体
            Map<String, Object> certRequest = new HashMap<>();
            certRequest.put("hosts", Arrays.asList("*." + certName, certName));
            certRequest.put("certificate", certData);
            certRequest.put("private_key", certKey);
            certRequest.put("bundle_method", "ubiquitous");
            certRequest.put("type", "custom");

            // 上传证书
            HttpData httpData = CDN_CLIENT.postBodyForData(API_URL + "/" + zoneId + "/custom_certificates", headers, JsonUtils.toString(certRequest));

            if (httpData.getStatusCode() == 200) {
                Map<String, Object> result = JsonUtils.parse(httpData.getResponseData(), new TypeReference<Map<String, Object>>() {
                });
                String certId = (String) ((Map<String, Object>) result.get("result")).get("id");
                return ResponseData.success(certId);
            } else {
                return ResponseData.errorMsg("Failed to upload SSL certificate: " + httpData.getResponseData());
            }
        } catch (Exception e) {
            return ResponseData.errorMsg("Error uploading SSL certificate: " + e.getMessage());
        }
    }


    /**
     * Vendor参数定义。
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @Schema(title = "Vendor参数", description = "Cloudflare CDN Vendor参数")
    public enum Param implements JsonConfigParam {
        API_KEY(JsonConfigParam.ParamType.STRING, "", "API Key", "API Key", ""),
        EMAIL(JsonConfigParam.ParamType.STRING, "", "API EMAIL", "API EMAIL", ""),
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
