package uw.gateway.center.controller.saas.access;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uw.auth.service.annotation.MscPermDeclare;
import uw.auth.service.constant.UserType;

/**
 * 主要是提供注解支持用。
 */
@RestController
public class $PackageInfo$ {

    @GetMapping("/saas/access")
    @Operation(summary = "访问统计", description = "鉴权访问统计日志")
    @MscPermDeclare(user = UserType.SAAS)
    public void info() {
    }

}
