package com.rookie.controller.common;

import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.customize.aop.permission.CheckRole;
import com.rookie.customize.aop.permission.CheckRoleAspect;
import com.rookie.enums.common.CheckModeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yayee
 */
@Api(value = "Test Interfaces", tags = "Test Interfaces")
@RestController
@RequestMapping("/test")
@Validated
@RequiredArgsConstructor
public class TestController {

    private final CheckRoleAspect aspect;

    @ApiOperation("测试角色")
    @GetMapping("testRole")
    public ResponseDTO<Void> testRole() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("没有admin权限就抛出异常");
        aspect.checkRoleAnd("admin");

        System.out.println("在【admin、user】中只要拥有一个就不会抛出异常");
        aspect.checkRoleOr("admin", "user");

        System.out.println("在【admin、user】中必须全部拥有才不会抛出异常");
        aspect.checkRoleAnd("admin", "user");

        System.out.println("角色测试通过");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色OR-admin,user,normal")
    @CheckRole(value = {"admin", "user", "normal"})
    @GetMapping("testRole/or")
    public ResponseDTO<Void> testRoleOR() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-OR(admin,user,normal)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色AND-user")
    @CheckRole(value = {"user"}, mode = CheckModeEnum.AND)
    @GetMapping("testRole/one")
    public ResponseDTO<Void> testRoleOne() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-AND(user)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色AND-admin,user")
    @CheckRole(value = {"admin", "user"}, mode = CheckModeEnum.AND)
    @GetMapping("testRole/and")
    public ResponseDTO<Void> testRoleAND() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-AND(admin,user)");
        return ResponseDTO.ok();
    }
}
