package uw.gateway.center.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 限速资源。
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "限速资源", description = "限速资源")
public enum RateLimitType {

    /**
     * 不限速
     */
    NONE(-1, "不限速"),

    /**
     * 以IP限速。
     */
    IP(0, "IP限速"),

    /**
     * SAAS限速。
     */
    SAAS(1, "SAAS限速"),

    /**
     * 用户ID限速
     */
    USER_TYPE(2, "用户类型限速"),

    /**
     * 用户ID限速
     */
    USER_ID(3, "用户ID限速"),

    /**
     * SAAS 和资源限速。
     */
    SAAS_URI(11, "SAAS资源限速"),

    /**
     * 用户和资源限速。
     */
    USER_TYPE_URI(12, "用户类型资源限速"),

    /**
     * 用户和资源限速。
     */
    USER_ID_URI(13, "用户ID资源限速");

    /**
     * 数值。
     */
    private final int value;

    /**
     * 标签。
     */
    private final String label;

    RateLimitType(int value, String label) {
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
    public static RateLimitType findByValue(int value) {
        for (RateLimitType target : RateLimitType.values()) {
            if (value == target.value) {
                return target;
            }
        }
        return NONE;
    }
}
