package com.rookie.customize.aop.permission;

import com.rookie.enums.common.CheckModeEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色认证校验：必须具有指定角色标识才能进入该方法。
 *
 * 可标注在方法、类上（效果等同于标注在此类的所有方法上）
 *
 * @author yayee
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CheckRole {

    /**
     * 需要校验的角色标识 [ 数组 ]
     */
    String[] value() default {};

    /**
     * 验证模式: AND | OR, 默认OR
     */
    CheckModeEnum mode() default CheckModeEnum.OR;
}
