package uw.gateway.center.conf;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * uw-gateway-center Swagger自动配置类
 */
@Configuration
@Profile({"default", "test", "dev"})
public class SwaggerConfig {

    /**
     * 应用名称
     */
    @Value("${project.name}")
    private String appName;

    /**
     * 应用版本
     */
    @Value("${project.version}")
    private String appVersion;

    @Bean
    public OpenApiCustomizer customOpenAPI() {
        return openApi -> openApi
                .addSecurityItem(new SecurityRequirement().addList("AuthToken"))
                .components(openApi.getComponents().addSecuritySchemes("AuthToken",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").in(SecurityScheme.In.HEADER)))
                .info(new Info().title(appName).version(appVersion)
                        .contact(new Contact().name("axeon").email("23231269@qq.com")));
    }

    /**
     * rootAPI接口。
     *
     * @return
     */
    @Bean
    public GroupedOpenApi rootApi() {
        return GroupedOpenApi.builder()
                .group("rootApi")
                .packagesToScan("uw.gateway.center.controller.root")
                .addOpenApiCustomizer(customOpenAPI())
                .build();
    }

    /**
     * ops API接口。
     *
     * @return
     */
    @Bean
    public GroupedOpenApi opsApi() {
        return GroupedOpenApi.builder()
                .group("opsApi")
                .addOpenApiCustomizer(customOpenAPI())
                .packagesToScan("uw.gateway.center.controller.ops")
                .build();
    }

    /**
     * adminAPI接口。
     *
     * @return
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("adminApi")
                .addOpenApiCustomizer(customOpenAPI())
                .packagesToScan("uw.gateway.center.controller.admin")
                .build();
    }

    /**
     * saasAPI接口。
     *
     * @return
     */
    @Bean
    public GroupedOpenApi saasApi() {
        return GroupedOpenApi.builder()
                .group("saasApi")
                .packagesToScan("uw.gateway.center.controller.saas")
                .addOpenApiCustomizer(customOpenAPI())
                .build();
    }

}
