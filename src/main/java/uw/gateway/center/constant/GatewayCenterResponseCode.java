package uw.gateway.center.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import uw.common.dto.ResponseCode;
import uw.common.util.EnumUtils;

/**
 * 代码信息。
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatewayCenterResponseCode implements ResponseCode {

    MSC_ACL_IP_DATA_ERROR( "IP地址数据错误！" ),

    MSC_ACL_EXISTS_ERROR("该配置已存在于ID[%]名称[%S]，请先删除再添加！")
    ;

    /**
     * 国际化信息MESSAGE_SOURCE。
     */
    private static final ResourceBundleMessageSource MESSAGE_SOURCE = new ResourceBundleMessageSource() {{
        setBasename( "i18n/messages/uw_auth_center" );
        setDefaultEncoding( "UTF-8" );
    }};

    private final String code;

    /**
     * 错误信息。
     */
    private final String message;

    GatewayCenterResponseCode(String message) {
        this.code = EnumUtils.enumNameToDotCase( this.name() );
        this.message = message;
    }

    /**
     * 获取配置前缀.
     *
     * @return
     */
    @Override
    public String codePrefix() {
        return "auth.center";
    }

    /**
     * 获取错误代码。
     *
     * @return
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取错误信息。
     *
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 获取消息源.
     *
     * @return
     */
    @Override
    public MessageSource getMessageSource() {
        return MESSAGE_SOURCE;
    }

}
