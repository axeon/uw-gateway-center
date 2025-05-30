package uw.gateway.center.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uw.gateway.center.conf.GatewayCenterProperties;
import uw.gateway.center.util.DingUtils;

/**
 * 钉钉发送服务。
 */
@Service
public class DingSendService {

    private static final Logger log = LoggerFactory.getLogger(DingSendService.class);

    /**
     * 系统名。
     */
    private static String centerName;

    /**
     * 钉钉通知配置。
     */
    private static GatewayCenterProperties.DingConfig notifyDingConfig;

    /**
     * 钉钉报警配置。
     */
    private static GatewayCenterProperties.DingConfig alertDingConfig;

    public DingSendService(GatewayCenterProperties gatewayCenterProperties) {
        DingSendService.centerName = gatewayCenterProperties.getCenterName();
        DingSendService.notifyDingConfig = gatewayCenterProperties.getNotifyDing();
        DingSendService.alertDingConfig = gatewayCenterProperties.getAlertDing();
    }

    /**
     * 发送notify。
     *
     * @param title
     * @param content
     */
    public static void sendNotify(String title, String content) {
        if (notifyDingConfig.isValid()) {
            if (log.isDebugEnabled()) {
                log.debug("发送Ding通知信息:title={},content={}", title, content);
            }
            if (StringUtils.isNotBlank(centerName)) {
                title = "[" + centerName + "]" + title;
            }
            content = "### " + title + "\n" + content;
            DingUtils.send(notifyDingConfig.getNotifyUrl(), notifyDingConfig.getNotifyKey() + title, content.toString());
        } else {
            log.error("发送Ding信息失败，请检查配置！title={},content={}", title, content);
        }
    }

    /**
     * 发送alert。
     *
     * @param title
     * @param content
     */
    public static void sendAlert(String title, String content) {
        if (alertDingConfig.isValid()) {
            if (log.isDebugEnabled()) {
                log.debug("发送Ding报警信息:title={},content={}", title, content);
            }
            if (StringUtils.isNotBlank(centerName)) {
                title = "[" + centerName + "]" + title;
            }
            content = "### " + title + "\n" + content;
            DingUtils.send(alertDingConfig.getNotifyUrl(), alertDingConfig.getNotifyKey() + title, content.toString());
        } else {
            log.error("发送Ding信息失败，请检查配置！title={},content={}", title, content);
        }
    }

}
