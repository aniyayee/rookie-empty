package com.rookie.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.security.SystemLoginUserHolder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author yayee
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (ObjectUtil.isEmpty(SystemLoginUserHolder.getSystemLoginUser())) {
            throw new RookieRuntimeException(ErrorCode.HTTP_STATUS_401);
        }
        log.debug("LoginInterceptor通过");
        return true;
    }
}
