package com.rookie.domain.system.role.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.domain.system.user.db.SysUserEntity;
import com.rookie.domain.system.user.db.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {

    private final SysUserMapper userMapper;

    @Override
    public boolean isRoleNameDuplicated(Long id, String roleName) {
        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(id != null, "role_id", id)
            .eq("role_name", roleName);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public boolean isRoleKeyDuplicated(Long id, String roleKey) {
        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(id != null, "role_id", id)
            .eq("role_key", roleKey);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public SysRoleEntity loadById(Long id) {
        SysRoleEntity byId = this.getById(id);
        if (byId == null) {
            throw new RookieRuntimeException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, id, "角色");
        }
        return byId;
    }

    @Override
    public boolean isAssignedToUsers(Long id) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        return userMapper.exists(queryWrapper);
    }
}
