package com.rookie.customize.aop.permission;

import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.customize.service.login.domain.SystemLoginUser;
import com.rookie.enums.common.CheckModeEnum;
import com.rookie.security.SystemLoginUserHolder;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 角色认证校验处理
 *
 * @author yayee
 */
@Aspect
@Component
@Slf4j
public class CheckRoleAspect {

    @Before("@annotation(roleChecker)")
    public void doBefore(JoinPoint joinPoint, CheckRole roleChecker) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("当前校验的方法:" + method.toGenericString());
        checkByAnnotation(roleChecker);
    }

    private void checkByAnnotation(CheckRole roleChecker) {
        String[] roleArray = roleChecker.value();
        if (roleChecker.mode() == CheckModeEnum.AND) {
            this.checkRoleAnd(roleArray);
        } else {
            this.checkRoleOr(roleArray);
        }
    }

    /**
     * 校验：当前账号是否含有指定角色标识 [ 指定多个，必须全部验证通过 ]
     *
     * @param roleArray 角色标识数组
     */
    public void checkRoleAnd(String... roleArray) {
        // 先获取当前是哪个账号
        SystemLoginUser loginUser = SystemLoginUserHolder.getSystemLoginUser();

        // 如果没有指定要校验的角色，那么直接跳过
        if (roleArray == null || roleArray.length == 0) {
            return;
        }

        // 开始校验
        String roleKey = loginUser.getRoleKey();
        for (String role : roleArray) {
            if (!StringUtils.equals(roleKey, role)) {
                throw new RookieRuntimeException(Business.PERMISSION_NOT_ALLOWED_TO_OPERATE);
            }
        }
    }

    /**
     * 校验：当前账号是否含有指定角色标识 [ 指定多个，只要其一验证通过即可 ]
     *
     * @param roleArray 角色标识数组
     */
    public void checkRoleOr(String... roleArray) {
        // 先获取当前是哪个账号
        SystemLoginUser loginUser = SystemLoginUserHolder.getSystemLoginUser();

        // 如果没有指定权限，那么直接跳过
        if (roleArray == null || roleArray.length == 0) {
            return;
        }

        // 开始校验
        String roleKey = loginUser.getRoleKey();
        for (String role : roleArray) {
            if (StringUtils.equals(roleKey, role)) {
                // 有的话提前退出
                return;
            }
        }

        // 代码至此，说明一个都没通过，需要抛出无角色异常
        throw new RookieRuntimeException(Business.PERMISSION_NOT_ALLOWED_TO_OPERATE);
    }
}
