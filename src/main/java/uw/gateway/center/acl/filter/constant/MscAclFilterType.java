package uw.gateway.center.acl.filter.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * IP过滤类型。
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "IP过滤类型", description = "IP过滤类型")
public enum MscAclFilterType {

    /**
     * 不限速
     */
    DENY_LIST( -1, "黑名单" ),

    /**
     * SAAS限速。
     */
    ALLOW_LIST( 1, "白名单" );

    /**
     * 数值。
     */
    private final int value;

    /**
     * 标签。
     */
    private final String label;

    MscAclFilterType(int value, String label) {
        this.value = value;
        this.label = label;
    }
    
    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
