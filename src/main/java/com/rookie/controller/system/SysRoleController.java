package com.rookie.controller.system;

import com.rookie.common.constants.RoleKeyConstants;
import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.core.page.PageDTO;
import com.rookie.customize.aop.accessLog.AccessLog;
import com.rookie.customize.aop.permission.CheckRole;
import com.rookie.domain.system.role.RoleApplicationService;
import com.rookie.domain.system.role.command.AddRoleCommand;
import com.rookie.domain.system.role.command.UpdateRoleCommand;
import com.rookie.domain.system.role.dto.RoleDTO;
import com.rookie.domain.system.role.query.RoleQuery;
import com.rookie.enums.common.BusinessTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author yayee
 */
@Api(value = "Role Interfaces", tags = "Role Interfaces")
@RestController
@RequestMapping("/system/role")
@Validated
@RequiredArgsConstructor
public class SysRoleController extends BaseController {

    private final RoleApplicationService roleApplicationService;

    @ApiOperation("Add role")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.TEST})
    @AccessLog(title = "角色管理", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    public ResponseDTO<Void> add(@Validated @RequestBody AddRoleCommand command) {
        roleApplicationService.addRole(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Edit role")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.TEST})
    @AccessLog(title = "角色管理", businessType = BusinessTypeEnum.MODIFY)
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@RequestBody UpdateRoleCommand command) {
        roleApplicationService.updateRole(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Delete role")
    @CheckRole(value = {RoleKeyConstants.ADMIN, RoleKeyConstants.TEST})
    @AccessLog(title = "角色管理", businessType = BusinessTypeEnum.DELETE)
    @PostMapping("/delete/{roleId}")
    public ResponseDTO<Void> remove(@PathVariable Long roleId) {
        roleApplicationService.deleteRole(roleId);
        return ResponseDTO.ok();
    }

    @ApiOperation("Query role By roleId")
    @GetMapping("/queryById/{roleId}")
    public ResponseDTO<RoleDTO> getInfo(@PathVariable Long roleId) {
        RoleDTO role = roleApplicationService.getRoleInfo(roleId);
        return ResponseDTO.ok(role);
    }

    @ApiOperation("Query role List")
    @GetMapping("/list")
    public ResponseDTO<PageDTO<RoleDTO>> list(RoleQuery query) {
        PageDTO<RoleDTO> pageDTO = roleApplicationService.getRoleList(query);
        return ResponseDTO.ok(pageDTO);
    }
}
