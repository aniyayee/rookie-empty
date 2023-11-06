package com.rookie.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @author yayee
 */
public class RegexUtil {

    /**
     * 是否是无效手机格式
     */
    public static boolean isPhoneInvalid(String phone) {
        return mismatch(phone, RegexPattern.PHONE_REGEX);
    }

    /**
     * 校验是否不符合正则格式
     */
    private static boolean mismatch(String str, String regex) {
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }

    static class RegexPattern {

        /**
         * 手机号正则
         */
        public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
    }
}
