package com.rookie.domain.role;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.common.core.page.PageDTO;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.common.exception.error.ErrorCode.Business;
import com.rookie.domain.role.command.AddRoleCommand;
import com.rookie.domain.role.command.UpdateRoleCommand;
import com.rookie.domain.role.db.ISysRoleService;
import com.rookie.domain.role.db.SysRoleEntity;
import com.rookie.domain.role.dto.RoleDTO;
import com.rookie.domain.role.query.RoleQuery;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author yayee
 */
@Service
@RequiredArgsConstructor
public class RoleApplicationService {

    private final ISysRoleService roleService;

    public void addRole(AddRoleCommand command) {
        SysRoleEntity entity = BeanUtil.copyProperties(command, SysRoleEntity.class);
        checkRoleNameUnique(entity.getRoleId(), entity.getRoleName());
        checkRoleKeyUnique(entity.getRoleId(), entity.getRoleKey());
        roleService.save(entity);
    }


    public void updateRole(UpdateRoleCommand command) {
        SysRoleEntity entity = roleService.loadById(command.getRoleId());
        BeanUtil.copyProperties(command, entity, "roleId");
        checkRoleNameUnique(entity.getRoleId(), entity.getRoleName());
        checkRoleKeyUnique(entity.getRoleId(), entity.getRoleKey());
        roleService.updateById(entity);
    }

    public void deleteRole(Long id) {
        SysRoleEntity entity = roleService.loadById(id);
        checkRoleCanBeDelete(id);
        roleService.removeById(entity);
    }

    public RoleDTO getRoleInfo(Long id) {
        SysRoleEntity entity = roleService.loadById(id);
        return new RoleDTO(entity);
    }

    public PageDTO<RoleDTO> getRoleList(RoleQuery query) {
        Page<SysRoleEntity> page = roleService.page(query.toPage(), query.toQueryWrapper());
        List<RoleDTO> records = page.getRecords().stream().map(RoleDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page);
    }

    public void checkRoleNameUnique(Long id, String roleName) {
        if (roleService.isRoleNameDuplicated(id, roleName)) {
            throw new RookieRuntimeException(ErrorCode.Business.ROLE_NAME_IS_NOT_UNIQUE, roleName);
        }
    }

    public void checkRoleKeyUnique(Long id, String roleKey) {
        if (roleService.isRoleKeyDuplicated(id, roleKey)) {
            throw new RookieRuntimeException(ErrorCode.Business.ROLE_KEY_IS_NOT_UNIQUE, roleKey);
        }
    }

    public void checkRoleCanBeDelete(Long id) {
        if (roleService.isAssignedToUsers(id)) {
            throw new RookieRuntimeException(Business.ROLE_ALREADY_ASSIGN_TO_USER);
        }
    }
}
