package com.rookie.domain.user.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    @Override
    public boolean isUserNameDuplicated(Long id, String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(id != null, "user_id", id)
            .eq("username", username);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public boolean isPhoneDuplicated(Long id, String phone) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(id != null, "user_id", id)
            .eq("phone", phone);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public SysUserEntity loadById(Long id) {
        SysUserEntity byId = this.getById(id);
        if (byId == null) {
            throw new RookieRuntimeException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, id, "用户");
        }
        return byId;
    }
}
