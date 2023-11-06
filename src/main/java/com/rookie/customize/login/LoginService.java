package com.rookie.customize.login;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.rookie.common.constants.RedisConstants;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.customize.login.command.LoginCommand;
import com.rookie.domain.common.dto.CurrentLoginUserDTO;
import com.rookie.domain.common.dto.TokenDTO;
import com.rookie.domain.system.user.UserApplicationService;
import com.rookie.domain.system.user.db.SysUserEntity;
import com.rookie.utils.RegexUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yayee
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginService {

    private final StringRedisTemplate stringRedisTemplate;

    private final UserApplicationService userApplicationService;

    public String generateCaptcha(String phone) {
        // 1.校验手机号
        if (RegexUtil.isPhoneInvalid(phone)) {
            throw new RookieRuntimeException(ErrorCode.Business.LOGIN_PHONE_FORMAT_ERROR);
        }
        // 2.生成验证码
        String captcha = RandomUtil.randomString(6);
        // 3.redis缓存
        stringRedisTemplate.opsForValue()
            .set(RedisConstants.LOGIN_CAPTCHA_KEY + phone, captcha, RedisConstants.LOGIN_CAPTCHA_TTL, TimeUnit.SECONDS);
        return captcha;
    }

    public TokenDTO login(LoginCommand loginCommand) {
        // 1.校验手机号和验证码
        String phone = loginCommand.getPhone();
        String captcha = loginCommand.getCaptcha();
        String cacheCaptcha = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CAPTCHA_KEY + phone);
        // 2.校验不通过
        if (!StringUtils.equals(cacheCaptcha, captcha)) {
            throw new RookieRuntimeException(Business.LOGIN_CAPTCHA_CODE_WRONG);
        }
        if (StringUtils.isBlank(cacheCaptcha)) {
            throw new RookieRuntimeException(Business.LOGIN_CAPTCHA_CODE_EXPIRE);
        }
        // 3.校验通过 查询用户是否注册
        SysUserEntity entity = userApplicationService.getUserByPhone(phone);
        if (ObjectUtils.isEmpty(entity)) {
            // 4.用户不存在 创建默认用户
            entity = userApplicationService.createDefaultUserWithPhone(phone);
        }
        CurrentLoginUserDTO userDTO = userApplicationService.getLoginUserInfo(entity.getUserId());
        // 5.保存用户信息到redis
        // 5.1 生成token
        String token = UUID.fastUUID().toString(true);
        // 5.2 dto转化位hash
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
            CopyOptions.create().setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 5.2 记录用户信息到redis
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 5.3 设置token过期时间
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return new TokenDTO(token, userDTO);
    }
}
