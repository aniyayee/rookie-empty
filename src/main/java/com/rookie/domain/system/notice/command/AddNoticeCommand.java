package com.rookie.domain.system.notice.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class AddNoticeCommand {

    @ApiModelProperty("公告标题")
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;

    @ApiModelProperty("公告类型（1通知 2公告）")
    private String noticeType;

    /**
     * 想要支持富文本的话, 避免Xss过滤的话, 需加上@JsonDeserialize(using = StringDeserializer.class) 注解
     */
    @ApiModelProperty("公告内容")
    @NotBlank
    @JsonDeserialize(using = StringDeserializer.class)
    private String noticeContent;

    @ApiModelProperty("公告状态（1正常 0关闭）")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
