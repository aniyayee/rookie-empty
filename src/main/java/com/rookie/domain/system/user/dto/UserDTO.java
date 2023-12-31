package com.rookie.domain.system.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rookie.domain.system.user.db.SysUserEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class UserDTO {

    public UserDTO(SysUserEntity entity) {
        if (entity != null) {
            this.userId = entity.getUserId();
            this.roleId = entity.getRoleId();
            this.username = entity.getUsername();
            this.password = entity.getPassword();
            this.nickname = entity.getNickname();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.remark = entity.getRemark();
            this.creatorId = entity.getCreatorId();
            this.createTime = entity.getCreateTime();
            this.updaterId = entity.getUpdaterId();
            this.updateTime = entity.getUpdateTime();
        }
    }

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户账号")
    private String username;

    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建者ID")
    private Long creatorId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新者ID")
    private Long updaterId;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
