package uw.gateway.center.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ACL审批状态
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "ACL审批状态", description = "ACL审批状态")
public enum AclAuditState {

    /**
     * 已取消
     */
    REJECT(-1, "审批拒绝"),
    /**
     * 待审批
     */
    INIT(0, "待审批"),
    /**
     * 审批通过
     */
    CONFIRM(1, "审批通过"),
    ;

    /**
     * 参数值
     */
    private final int value;

    /**
     * 参数信息。
     */
    private final String label;

    AclAuditState(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static AclAuditState findByValue(int value) {
        for (AclAuditState e : AclAuditState.values()) {
            if (value == e.value) {
                return e;
            }
        }
        return null;
    }

    /**
     * 检查状态值是否合法。
     *
     * @param value
     * @return
     */
    public static boolean checkStateValid(int value) {
        for (AclAuditState state : AclAuditState.values()) {
            if (value == state.value) {
                return true;
            }
        }
        return false;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
