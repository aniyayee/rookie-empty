package com.rookie.domain.system.notice;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.common.core.page.PageDTO;
import com.rookie.domain.system.notice.command.AddNoticeCommand;
import com.rookie.domain.system.notice.command.UpdateNoticeCommand;
import com.rookie.domain.system.notice.db.ISysNoticeService;
import com.rookie.domain.system.notice.db.SysNoticeEntity;
import com.rookie.domain.system.notice.dto.NoticeDTO;
import com.rookie.domain.system.notice.query.NoticeQuery;
import com.rookie.domain.system.user.db.ISysUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author yayee
 */
@Service
@RequiredArgsConstructor
public class NoticeApplicationService {

    private final ISysNoticeService noticeService;

    private final ISysUserService userService;

    public void addNotice(AddNoticeCommand command) {
        SysNoticeEntity entity = BeanUtil.copyProperties(command, SysNoticeEntity.class);

        noticeService.save(entity);
    }

    public void updateNotice(UpdateNoticeCommand command) {
        SysNoticeEntity entity = noticeService.loadById(command.getNoticeId());
        BeanUtil.copyProperties(command, entity, "noticeId");

        noticeService.updateById(entity);
    }

    public void deleteNotice(Long id) {
        SysNoticeEntity entity = noticeService.loadById(id);
        noticeService.removeById(entity);
    }

    public NoticeDTO getNoticeInfo(Long id) {
        SysNoticeEntity entity = noticeService.loadById(id);
        return getNoticeDTO(entity);
    }

    public PageDTO<NoticeDTO> getNoticeList(NoticeQuery query) {
        Page<SysNoticeEntity> page = noticeService.page(query.toPage(), query.toQueryWrapper());
        List<NoticeDTO> records = page.getRecords().stream().map(this::getNoticeDTO).collect(Collectors.toList());
        return new PageDTO<>(records, page);
    }

    private NoticeDTO getNoticeDTO(SysNoticeEntity entity) {
        String creatorName = userService.loadById(entity.getCreatorId()).getUsername();
        String updaterName = null;
        if (entity.getUpdaterId() != null) {
            updaterName = userService.loadById(entity.getUpdaterId()).getUsername();
        }
        return new NoticeDTO(entity, creatorName, updaterName);
    }
}
