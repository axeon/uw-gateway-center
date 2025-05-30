package uw.gateway.center.conf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * GatewayCenterProperties。
 */
@ConfigurationProperties(prefix = "uw.gateway.center")
public class GatewayCenterProperties {

    /**
     * 系统名称。
     */
    private String centerName = "网关管理中心";

    /**
     * 系统外网访问地址。
     */
    private String centerSite;

    /**
     * 访问日志ES配置
     */
    private EsConfig accessLogEs = new EsConfig();

    /**
     * 部署的钉钉通知。
     */
    private DingConfig notifyDing = new DingConfig("","GW");

    /**
     * 报警的钉钉通知。
     */
    private DingConfig alertDing = new DingConfig("","GW");

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterSite() {
        return centerSite;
    }

    public void setCenterSite(String centerSite) {
        this.centerSite = centerSite;
    }

    public EsConfig getAccessLogEs() {
        return accessLogEs;
    }

    public void setAccessLogEs(EsConfig accessLogEs) {
        this.accessLogEs = accessLogEs;
    }

    public DingConfig getNotifyDing() {
        return notifyDing;
    }

    public void setNotifyDing(DingConfig notifyDing) {
        this.notifyDing = notifyDing;
    }

    public DingConfig getAlertDing() {
        return alertDing;
    }

    public void setAlertDing(DingConfig alertDing) {
        this.alertDing = alertDing;
    }

    /**
     * ES主机配置
     */
    public static class EsConfig {

        /**
         * 连接超时
         */
        private long connectTimeout = 30000;

        /**
         * 读超时
         */
        private long readTimeout = 30000;

        /**
         * 写超时
         */
        private long writeTimeout = 30000;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * ES集群HTTP REST地址
         */
        private String server = null;

        public long getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public long getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
        }

        public long getWriteTimeout() {
            return writeTimeout;
        }

        public void setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }
    }

    /**
     * 钉钉通知配置。
     */
    public static class DingConfig {

        /**
         * 通知地址。
         */
        private String notifyUrl;

        /**
         * 通知key。
         */
        private String notifyKey;

        /**
         * 构造函数。
         * @param notifyUrl
         * @param notifyKey
         */
        public DingConfig(String notifyUrl, String notifyKey) {
            this.notifyUrl = notifyUrl;
            this.notifyKey = notifyKey;
        }

        /**
         * 构造函数。
         */
        public DingConfig() {
        }

        /**
         * 检查配置是否符合。
         *
         * @return
         */
        public boolean isValid() {
            return StringUtils.isNotBlank( notifyKey ) && StringUtils.isNotBlank( notifyUrl );
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public String getNotifyKey() {
            return notifyKey;
        }

        public void setNotifyKey(String notifyKey) {
            this.notifyKey = notifyKey;
        }
    }


}
