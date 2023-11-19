package com.rookie.controller.common;

import com.rookie.common.constants.RoleKeyConstants;
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

    @ApiOperation("测试角色OR-admin,test,normal")
    @CheckRole(value = {"admin", "test", "normal"})
    @GetMapping("testRole/or/all")
    public ResponseDTO<Void> testRoleORAll() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-OR(admin,test,normal)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色OR-admin,test")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.TEST})
    @GetMapping("testRole/or/admin-test")
    public ResponseDTO<Void> testRoleORAdminTest() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-OR(admin,test)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色OR-admin,normal")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.NORMAL})
    @GetMapping("testRole/or/admin-normal")
    public ResponseDTO<Void> testRoleORAdminNormal() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-OR(admin,normal)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色OR-test")
    @CheckRole(value = {RoleKeyConstants.TEST})
    @GetMapping("testRole/or/test")
    public ResponseDTO<Void> testRoleORTest() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-OR(test)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色AND-admin,test,normal")
    @CheckRole(value = {"admin", "test", "normal"}, mode = CheckModeEnum.AND)
    @GetMapping("testRole/and/all")
    public ResponseDTO<Void> testRoleANDAll() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-AND(admin,test,normal)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色AND-admin,test")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.TEST}, mode = CheckModeEnum.AND)
    @GetMapping("testRole/and/admin-test")
    public ResponseDTO<Void> testRoleANDAdminTest() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-AND(admin,test)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色AND-admin,normal")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.NORMAL}, mode = CheckModeEnum.AND)
    @GetMapping("testRole/and/admin-normal")
    public ResponseDTO<Void> testRoleANDAdminNormal() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-AND(admin,normal)");
        return ResponseDTO.ok();
    }

    @ApiOperation("测试角色AND-test")
    @CheckRole(value = {RoleKeyConstants.TEST}, mode = CheckModeEnum.AND)
    @GetMapping("testRole/and/test")
    public ResponseDTO<Void> testRoleANDTest() {
        System.out.println("======================= 进入方法，测试角色接口 ========================= ");

        System.out.println("角色测试通过-AND(test)");
        return ResponseDTO.ok();
    }
}
