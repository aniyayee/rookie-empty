package com.rookie.domain.system.role.db;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author yayee
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    boolean isRoleNameDuplicated(Long id, String roleName);

    boolean isRoleKeyDuplicated(Long id, String roleKey);

    SysRoleEntity loadById(Long id);

    boolean isAssignedToUsers(Long id);
}
