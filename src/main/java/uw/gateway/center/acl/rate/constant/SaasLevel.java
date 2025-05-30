package uw.gateway.center.acl.rate.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SAAS等级。
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "SAAS等级", description = "SAAS等级")
public enum SaasLevel {

    /**
     * 不限速
     */
    NONE(-1, "不限制"),

    /**
     * 普通级别。
     */
    NORMAL(0, "普通级别"),

    /**
     * VIP级别。
     */
    VIP(1, "VIP级别"),

    /**
     * SVIP级别。
     */
    SVIP(2, "SVIP级别"),

    /**
     * KVIP级别
     */
    KVIP(3, "KVIP级别"),

    /**
     * 系统自身
     */
    SYS(9, "系统自身");

    /**
     * 数值。
     */
    private final int value;

    /**
     * 标签。
     */
    private final String label;

    SaasLevel(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }


    /**
     * 根据value获取enum。
     *
     * @param value
     * @return
     */
    public static SaasLevel findByValue(int value) {
        for (SaasLevel target : SaasLevel.values()) {
            if (value == target.value) {
                return target;
            }
        }
        return NONE;
    }
}
