package com.rookie.domain.common.dto;

import com.rookie.domain.system.user.db.SysUserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yayee
 */
@Data
@NoArgsConstructor
public class CurrentLoginUserDTO {

    public CurrentLoginUserDTO(SysUserEntity entity, String roleKey) {
        this.userId = entity.getUserId();
        this.roleId = entity.getRoleId();
        this.username = entity.getUsername();
        this.nickname = entity.getNickname();
        this.roleKey = roleKey;
    }

    private Long userId;

    private Long roleId;

    private String username;

    private String nickname;

    private String roleKey;
}
