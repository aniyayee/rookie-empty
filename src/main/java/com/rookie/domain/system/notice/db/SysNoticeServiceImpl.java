package com.rookie.domain.system.notice.db;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author yayee
 * @since 2023-11-16
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNoticeEntity> implements ISysNoticeService {

    @Override
    public SysNoticeEntity loadById(Long id) {
        SysNoticeEntity byId = this.getById(id);
        if (byId == null) {
            throw new RookieRuntimeException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, id, "通知公告");
        }
        return byId;
    }
}
