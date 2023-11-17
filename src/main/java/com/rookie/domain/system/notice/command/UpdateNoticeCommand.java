package com.rookie.domain.system.notice.command;

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
public class UpdateNoticeCommand extends AddNoticeCommand {

    @ApiModelProperty("公告ID")
    @NotNull
    @Positive
    private Long noticeId;
}
