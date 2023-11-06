package com.rookie.customize.user.web;

import lombok.Data;

/**
 * @author yayee
 */
@Data
public class SystemLoginUser {

    private Long userId;
    private Long roleId;
    private String username;
    private String roleKey;
}
