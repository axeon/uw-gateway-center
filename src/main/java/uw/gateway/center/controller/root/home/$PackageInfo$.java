package uw.gateway.center.controller.root.home;

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
    @GetMapping("/root/home")
    @Operation(summary = "首页", description = "首页")
    @MscPermDeclare(user = UserType.ROOT)
    public void info() {
    }

}
