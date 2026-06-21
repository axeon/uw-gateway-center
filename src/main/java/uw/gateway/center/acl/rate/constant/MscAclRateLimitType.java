package uw.gateway.center.acl.rate.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 限速资源。
 * SAAS_LEVEL和SAAS_ID是互斥选择。
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "限速资源", description = "限速资源")
public enum MscAclRateLimitType {

    /**
     * 不限速
     */
    NONE(-1, "不限速"),

    /**
     * 以IP限速。
     */
    IP(0, "IP限速"),

    /**
     * SAAS等级。
     */
    SAAS_LEVEL(1, "SAAS等级限速"),

    /**
     * 用户ID限速
     */
    USER_TYPE(2, "用户类型限速"),

    /**
     * 用户ID限速
     */
    USER_ID(3, "用户ID限速"),

    /**
     * SAAS等级资源限速。
     */
    SAAS_LEVEL_URI(11, "SAAS等级资源限速"),

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

    MscAclRateLimitType(int value, String label) {
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
    public static MscAclRateLimitType findByValue(int value) {
        for (MscAclRateLimitType target : MscAclRateLimitType.values()) {
            if (value == target.value) {
                return target;
            }
        }
        return NONE;
    }

    /**
     * 严格判断 value 是否为合法的限速类型值。
     * <p>
     * 与 {@link #findByValue(int)} 的区别：后者对非法值会回退为 {@link #NONE}，无法区分"真的传 NONE"与"传了非法值"；
     * 本方法用于参数校验场景，只有 value 命中某个枚举常量才返回 true。
     *
     * @param value 待校验的限速类型值
     * @return true=合法，false=非法
     */
    public static boolean isValidValue(int value) {
        for (MscAclRateLimitType target : MscAclRateLimitType.values()) {
            if (value == target.value) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断本限速类型是否需要按用户维度（userType/userId）限速。
     * <p>
     * {@link #USER_TYPE}、{@link #USER_ID}、{@link #USER_TYPE_URI}、{@link #USER_ID_URI} 这四种类型
     * 要求调用方提供 userType/userId；其余类型不依赖用户维度。
     *
     * @return true=需要按用户维度限速
     */
    public boolean isUserDimension() {
        return this == USER_TYPE || this == USER_ID || this == USER_TYPE_URI || this == USER_ID_URI;
    }
}
