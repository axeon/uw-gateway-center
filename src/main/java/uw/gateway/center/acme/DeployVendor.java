package uw.gateway.center.acme;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import uw.common.app.vo.JsonConfigBox;
import uw.common.app.vo.JsonConfigParam;
import uw.common.dto.ResponseData;

import java.util.List;

/**
 * 发布供应商接口。
 */
@Schema(title = "发布供应商接口", description = "发布供应商接口")
public interface DeployVendor {

    /**
     * 供应商名称
     */
    @JsonProperty("vendorName")
    @Schema(title = "供应商名称", description = "供应商名称")
    String vendorName();

    /**
     * 供应商描述
     */
    @JsonProperty("vendorDesc")
    @Schema(title = "供应商描述", description = "供应商描述")
    String vendorDesc();

    /**
     * 供应商版本
     */
    @JsonProperty("vendorVersion")
    @Schema(title = "供应商版本", description = "供应商版本")
    String vendorVersion();

    /**
     * 供应商图标
     */
    @JsonProperty("vendorIcon")
    @Schema(title = "供应商图标", description = "供应商图标")
    String vendorIcon();

    /**
     * 供应商类名
     */
    @JsonProperty("vendorClass")
    @Schema(title = "供应商类名", description = "供应商类名")
    default String vendorClass() {
        return this.getClass().getName();
    }

    /**
     * Vendor参数信息集合，管理员可见。
     */
    @JsonProperty("vendorParam")
    @Schema(title = "Vendor参数信息集合", description = "Vendor参数信息集合，管理员可见。")
    List<JsonConfigParam> vendorParam();

    /**
     * 初始化API凭证
     *
     * @param config 特定的DNS服务商凭证
     * @return
     */
    DeployVendor build(JsonConfigBox config);

    /**
     * 添加ssl证书。
     *
     * @return 记录ID或其他标识
     */
    ResponseData<String> deployCert(String certName, String certKey, String certData);

}