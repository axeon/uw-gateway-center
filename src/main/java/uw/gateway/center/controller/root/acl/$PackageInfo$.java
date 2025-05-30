package uw.gateway.center.controller.root.acl;

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

    @GetMapping("/root/acl")
    @Operation(summary = "系统访问控制", description = "系统访问控制")
    @MscPermDeclare(user = UserType.ROOT)
    public void info() {
    }

}
