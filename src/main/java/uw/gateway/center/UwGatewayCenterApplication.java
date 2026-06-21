package uw.gateway.center;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import uw.common.app.AppBootStrap;

/**
 * uw-gateway-center 启动入口。
 * <p>
 * 网关管理中心，提供网关访问控制（ACL）、限速、ACME 证书管理、访问日志统计等能力的配置与管理后台，
 * 并通过 RPC 接口向 uw-gateway 下发配置、向 uw-gateway-client 暴露运营商限速管理能力。
 *
 * @author axeon
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UwGatewayCenterApplication {

    /**
     * 应用启动入口。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        AppBootStrap.run(UwGatewayCenterApplication.class, args);
    }

}
