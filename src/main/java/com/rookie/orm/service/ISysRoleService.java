package com.rookie.orm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.domain.role.command.AddRoleCommand;
import com.rookie.domain.role.command.UpdateRoleCommand;
import com.rookie.domain.role.dto.SysRoleDTO;
import com.rookie.domain.role.query.RoleQuery;
import com.rookie.orm.entity.SysRoleEntity;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author yayee
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    void addRole(AddRoleCommand command);

    void updateRole(UpdateRoleCommand command);

    void deleteById(Long roleId);

    SysRoleDTO queryById(Long roleId);

    List<SysRoleDTO> findList(RoleQuery roleQueryBean);

    SysRoleDTO queryByRoleName(String roleName);

    boolean isRoleNameDuplicated(String roleName);
}
