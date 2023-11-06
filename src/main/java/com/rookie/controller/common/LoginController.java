package com.rookie.controller.common;

import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.customize.login.LoginService;
import com.rookie.customize.login.command.LoginCommand;
import com.rookie.customize.user.SystemLoginUserHolder;
import com.rookie.customize.user.web.SystemLoginUser;
import com.rookie.domain.common.dto.CurrentLoginUserDTO;
import com.rookie.domain.common.dto.TokenDTO;
import com.rookie.domain.system.user.UserApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yayee
 */
@Api(value = "登录API", tags = "登录相关接口")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final UserApplicationService userApplicationService;

    /**
     * 生成验证码
     */
    @ApiOperation("验证码")
    @GetMapping("/captcha/{phone}")
    public ResponseDTO<String> getCaptcha(@PathVariable String phone) {
        String captcha = loginService.generateCaptcha(phone);
        return ResponseDTO.ok(captcha);
    }

    /**
     * 登录方法
     *
     * @param loginCommand 登录信息
     * @return 结果
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseDTO<TokenDTO> login(@RequestBody LoginCommand loginCommand) {
        TokenDTO tokenDTO = loginService.login(loginCommand);
        return ResponseDTO.ok(tokenDTO);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public ResponseDTO<CurrentLoginUserDTO> getLoginUserInfo() {
        SystemLoginUser loginUser = SystemLoginUserHolder.getSystemLoginUser();
        CurrentLoginUserDTO currentUserDTO = userApplicationService.getLoginUserInfo(loginUser.getUserId());

        return ResponseDTO.ok(currentUserDTO);
    }
}
