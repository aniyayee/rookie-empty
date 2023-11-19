package com.rookie.enums.common;

/**
 * 注解鉴权的验证模式
 *
 * @author yayee
 */
public enum CheckModeEnum {

    /**
     * 必须具有所有的元素
     */
    AND,

    /**
     * 只需具有其中一个元素
     */
    OR
}
