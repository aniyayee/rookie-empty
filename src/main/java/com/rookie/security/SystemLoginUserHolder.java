package com.rookie.security;

import com.rookie.customize.service.login.domain.SystemLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author yayee
 */
@Slf4j
public class SystemLoginUserHolder {

    private static final ThreadLocal<SystemLoginUser> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveSystemLoginUser(SystemLoginUser user) {
        if (ObjectUtils.isNotEmpty(user)) {
            THREAD_LOCAL.set(user);
            log.debug("[ADD THREAD_LOCAL: {}]", user);
        }
    }

    public static Long getUserId() {
        return getSystemLoginUser().getUserId();
    }

    public static SystemLoginUser getSystemLoginUser() {
        SystemLoginUser loginUser = THREAD_LOCAL.get();
        log.debug("[GET THREAD_LOCAL: {}]", loginUser);
        return loginUser;
    }

    public static void removeSystemLoginUser() {
        THREAD_LOCAL.remove();
        log.debug("[REMOVE THREAD_LOCAL]");
    }
}
