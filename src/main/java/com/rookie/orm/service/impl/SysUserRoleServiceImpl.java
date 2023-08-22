package com.rookie.orm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.orm.entity.SysUserRoleEntity;
import com.rookie.orm.mapper.SysUserRoleMapper;
import com.rookie.orm.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author yayee
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements
    ISysUserRoleService {

}
