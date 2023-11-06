package com.rookie.domain.system.role.command;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateRoleCommand extends AddRoleCommand {

    @ApiModelProperty("角色ID")
    @NotNull(message = "could not be empty")
    @Positive(message = "must be a positive number")
    private Long roleId;
}
