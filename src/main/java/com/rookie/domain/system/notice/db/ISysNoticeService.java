package com.rookie.domain.system.notice.db;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author yayee
 * @since 2023-11-16
 */
public interface ISysNoticeService extends IService<SysNoticeEntity> {

    SysNoticeEntity loadById(Long id);
}
