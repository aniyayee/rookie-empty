package com.rookie.controller.system;

import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.core.page.PageDTO;
import com.rookie.customize.aop.accessLog.AccessLog;
import com.rookie.domain.system.notice.NoticeApplicationService;
import com.rookie.domain.system.notice.command.AddNoticeCommand;
import com.rookie.domain.system.notice.command.UpdateNoticeCommand;
import com.rookie.domain.system.notice.dto.NoticeDTO;
import com.rookie.domain.system.notice.query.NoticeQuery;
import com.rookie.enums.common.BusinessTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author yayee
 * @since 2023-11-16
 */
@Api(value = "Notice Interfaces", tags = "Notice Interfaces")
@RestController
@RequestMapping("/system/notice")
@Validated
@RequiredArgsConstructor
public class SysNoticeController extends BaseController {

    private final NoticeApplicationService noticeApplicationService;

    @ApiOperation("添加公告")
    @AccessLog(title = "通知公告", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    public ResponseDTO<Void> add(@RequestBody AddNoticeCommand command) {
        noticeApplicationService.addNotice(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("修改公告")
    @AccessLog(title = "通知公告", businessType = BusinessTypeEnum.MODIFY)
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@RequestBody UpdateNoticeCommand command) {
        noticeApplicationService.updateNotice(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("删除公告")
    @AccessLog(title = "通知公告", businessType = BusinessTypeEnum.DELETE)
    @PostMapping("/delete/{noticeId}")
    public ResponseDTO<Void> remove(@PathVariable Long noticeId) {
        noticeApplicationService.deleteNotice(noticeId);
        return ResponseDTO.ok();
    }

    @ApiOperation("公告详情")
    @GetMapping("/queryById/{noticeId}")
    public ResponseDTO<NoticeDTO> getInfo(@PathVariable @NotNull @Positive Long noticeId) {
        NoticeDTO notice = noticeApplicationService.getNoticeInfo(noticeId);
        return ResponseDTO.ok(notice);
    }

    @ApiOperation("公告列表")
    @GetMapping("/list")
    public ResponseDTO<PageDTO<NoticeDTO>> list(NoticeQuery query) {
        PageDTO<NoticeDTO> pageDTO = noticeApplicationService.getNoticeList(query);
        return ResponseDTO.ok(pageDTO);
    }
}
