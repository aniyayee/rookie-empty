package com.rookie.controller.system;

import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.core.page.PageDTO;
import com.rookie.customize.aop.accessLog.AccessLog;
import com.rookie.domain.system.user.UserApplicationService;
import com.rookie.domain.system.user.command.AddUserCommand;
import com.rookie.domain.system.user.command.UpdateUserCommand;
import com.rookie.domain.system.user.command.UpdateUserPasswordCommand;
import com.rookie.domain.system.user.dto.UserDTO;
import com.rookie.domain.system.user.query.UserQuery;
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
 * 用户信息表 前端控制器
 * </p>
 *
 * @author yayee
 */
@Api(value = "User Interfaces", tags = "User Interfaces")
@RestController
@RequestMapping("/system/user")
@Validated
@RequiredArgsConstructor
public class SysUserController extends BaseController {

    private final UserApplicationService userApplicationService;

    @ApiOperation("Add user")
    @AccessLog(title = "用户管理", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    public ResponseDTO<Void> add(@Validated @RequestBody AddUserCommand command) {
        userApplicationService.addUser(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Edit user")
    @AccessLog(title = "用户管理", businessType = BusinessTypeEnum.MODIFY)
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@RequestBody UpdateUserCommand command) {
        userApplicationService.updateUser(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Delete user")
    @AccessLog(title = "用户管理", businessType = BusinessTypeEnum.DELETE)
    @PostMapping("/delete/{userId}")
    public ResponseDTO<Void> remove(@PathVariable Long userId) {
        userApplicationService.deleteUser(userId);
        return ResponseDTO.ok();
    }

    @ApiOperation("Query user By userId")
    @GetMapping("/queryById/{userId}")
    public ResponseDTO<UserDTO> getInfo(@PathVariable Long userId) {
        UserDTO user = userApplicationService.getUserInfo(userId);
        return ResponseDTO.ok(user);
    }

    @ApiOperation("Query user List")
    @GetMapping("/list")
    public ResponseDTO<PageDTO<UserDTO>> list(UserQuery query) {
        PageDTO<UserDTO> pageDTO = userApplicationService.getUserList(query);
        return ResponseDTO.ok(pageDTO);
    }

    @ApiOperation("Update user password")
    @AccessLog(title = "用户管理", businessType = BusinessTypeEnum.MODIFY)
    @PostMapping("/password")
    public ResponseDTO<Void> updatePassword(@RequestBody UpdateUserPasswordCommand command) {
        userApplicationService.updatePassword(command);
        return ResponseDTO.ok();
    }
}
