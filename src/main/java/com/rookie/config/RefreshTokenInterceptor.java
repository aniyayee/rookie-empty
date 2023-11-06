package com.rookie.config;

import cn.hutool.core.bean.BeanUtil;
import com.rookie.common.constants.Constants;
import com.rookie.common.constants.RedisConstants;
import com.rookie.customize.user.SystemLoginUserHolder;
import com.rookie.customize.user.web.SystemLoginUser;
import com.rookie.domain.common.dto.CurrentLoginUserDTO;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author yayee
 */
@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader(Constants.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            log.debug("[TOKEN为空]");
            return true;
        }
        // 2.基于token获取redis中的用户
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        if (ObjectUtils.isEmpty(userMap)) {
            log.debug("[REDIS缓存为空]");
            return true;
        }
        // 3.将查询到的hash数据转为UserDTO
        CurrentLoginUserDTO dto = BeanUtil.fillBeanWithMap(userMap, new CurrentLoginUserDTO(), false);
        // 4.保存用户信息到 ThreadLocal
        SystemLoginUserHolder.saveSystemLoginUser(BeanUtil.copyProperties(dto, SystemLoginUser.class));
        // 5.刷新token过期时间
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        SystemLoginUserHolder.removeSystemLoginUser();
    }
}
