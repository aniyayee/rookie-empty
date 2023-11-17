package com.rookie.domain.system.notice.dto;

import com.rookie.domain.system.notice.db.SysNoticeEntity;
import java.util.Date;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class NoticeDTO {

    public NoticeDTO(SysNoticeEntity entity, String creatorName, String updaterName) {
        if (entity != null) {
            this.noticeId = entity.getNoticeId();
            this.noticeTitle = entity.getNoticeTitle();
            this.noticeType = entity.getNoticeType();
            this.noticeContent = entity.getNoticeContent();
            this.status = entity.getStatus();
            this.remark = entity.getRemark();
            this.creatorId = entity.getCreatorId();
            this.createTime = entity.getCreateTime();
            this.updaterId = entity.getUpdaterId();
            this.updateTime = entity.getUpdateTime();
        }
        this.creatorName = creatorName;
        this.updaterName = updaterName;
    }

    private Long noticeId;

    private String noticeTitle;

    private Integer noticeType;

    private String noticeContent;

    private Integer status;

    private String remark;

    private Long creatorId;

    private Date createTime;

    private Long updaterId;

    private Date updateTime;

    private String creatorName;
    private String updaterName;
}
