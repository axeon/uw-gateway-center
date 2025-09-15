package uw.gateway.center;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import uw.common.app.AppBootStrap;

@SpringBootApplication
@EnableDiscoveryClient
public class UwGatewayCenterApplication {

    public static void main(String[] args) {
        AppBootStrap.run(UwGatewayCenterApplication.class, args);
    }

}
