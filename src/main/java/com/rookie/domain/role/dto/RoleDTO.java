package com.rookie.domain.role.dto;

import com.rookie.domain.role.db.SysRoleEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class RoleDTO {

    public RoleDTO(SysRoleEntity entity) {
        if (entity != null) {
            this.roleId = entity.getRoleId();
            this.roleName = entity.getRoleName();
            this.roleKey = entity.getRoleKey();
            this.remark = entity.getRemark();
        }
    }

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色权限字符串")
    private String roleKey;

    @ApiModelProperty("备注")
    private String remark;
}
