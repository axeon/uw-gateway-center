package base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import uw.gateway.center.UwGatewayCenterApplication;

/**
 * 测试基类
 */
@SpringBootTest(classes = UwGatewayCenterApplication.class)
@ContextConfiguration(loader = SaasAppApplicationLoader.class)
public abstract class SaasAppApplicationBaseIT {

    /**
     * BeforeClass
     */
    @BeforeAll
    public static void beforeClass() {
        System.out.println("beforeClass.........................");
    }

    /**
     * AfterClass
     */
    @AfterAll
    public static void afterClass() {
        System.out.println("afterClass.........................");
    }
}
