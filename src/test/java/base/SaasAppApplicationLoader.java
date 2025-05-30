package base;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import uw.gateway.center.UwGatewayCenterApplication;

/**
 * 自定义Spring Boot 测试加载器
 */
public class SaasAppApplicationLoader extends SpringBootContextLoader {

    /**
     * 默认名称生成器
     */
    private BeanNameGenerator componentScanBeanNameGenerator = new AnnotationBeanNameGenerator();

    @Override
    protected SpringApplication getSpringApplication() {
        return new SpringApplicationBuilder(UwGatewayCenterApplication.class)
                .beanNameGenerator((beanDefinition, beanDefinitionRegistry) -> {
                    String beanClassName = beanDefinition.getBeanClassName();
                    if (beanClassName.startsWith("saas")) {
                        return beanClassName;
                    }
                    return componentScanBeanNameGenerator.generateBeanName(beanDefinition, beanDefinitionRegistry);
                }).application();
    }
}
