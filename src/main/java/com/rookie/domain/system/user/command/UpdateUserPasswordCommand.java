package com.rookie.domain.system.user.command;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author yayee
 */
@Data
public class UpdateUserPasswordCommand {

    @ApiModelProperty("用户ID")
    @NotNull(message = "could not be empty")
    @Positive(message = "must be a positive number")
    private Long userId;

    @ApiModelProperty("新密码")
    @NotBlank(message = "could not be empty")
    @Length(min = 1, max = 32, message = "password should be 1-32")
    private String newPassword;

    @ApiModelProperty("旧密码")
    @NotBlank(message = "could not be empty")
    @Length(min = 1, max = 32, message = "password should be 1-32")
    private String oldPassword;
}
