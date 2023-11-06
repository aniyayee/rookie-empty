package com.rookie.domain.system.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.common.constants.Constants;
import com.rookie.common.core.page.PageDTO;
import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.domain.common.dto.CurrentLoginUserDTO;
import com.rookie.domain.system.role.db.ISysRoleService;
import com.rookie.domain.system.user.command.AddUserCommand;
import com.rookie.domain.system.user.command.UpdateUserCommand;
import com.rookie.domain.system.user.command.UpdateUserPasswordCommand;
import com.rookie.domain.system.user.db.ISysUserService;
import com.rookie.domain.system.user.db.SysUserEntity;
import com.rookie.domain.system.user.dto.UserDTO;
import com.rookie.domain.system.user.query.UserQuery;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author yayee
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final ISysUserService userService;

    private final ISysRoleService roleService;

    public void addUser(AddUserCommand command) {
        SysUserEntity entity = BeanUtil.copyProperties(command, SysUserEntity.class);
        checkUserNameUnique(entity.getUserId(), entity.getUsername());
        checkPhoneUnique(entity.getUserId(), entity.getPhone());
        userService.save(entity);
    }

    public void updateUser(UpdateUserCommand command) {
        SysUserEntity entity = userService.loadById(command.getUserId());
        BeanUtil.copyProperties(command, entity, "userId");
        checkUserNameUnique(entity.getUserId(), entity.getUsername());
        checkPhoneUnique(entity.getUserId(), entity.getPhone());
        userService.updateById(entity);
    }

    public void deleteUser(Long id) {
        SysUserEntity entity = userService.loadById(id);
        userService.removeById(entity);
    }

    public UserDTO getUserInfo(Long id) {
        SysUserEntity entity = userService.loadById(id);
        return new UserDTO(entity);
    }

    public PageDTO<UserDTO> getUserList(UserQuery query) {
        Page<SysUserEntity> page = userService.page(query.toPage(), query.toQueryWrapper());
        List<UserDTO> records = page.getRecords().stream().map(UserDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page);
    }

    public void updatePassword(UpdateUserPasswordCommand command) {
        // TODO loginUser
    }

    public void checkUserNameUnique(Long id, String username) {
        if (userService.isUserNameDuplicated(id, username)) {
            throw new RookieRuntimeException(ErrorCode.Business.USER_NAME_IS_NOT_UNIQUE);
        }
    }

    public void checkPhoneUnique(Long id, String phone) {
        if (userService.isPhoneDuplicated(id, phone)) {
            throw new RookieRuntimeException(ErrorCode.Business.USER_PHONE_NUMBER_IS_NOT_UNIQUE);
        }
    }

    public SysUserEntity getUserByPhone(String phone) {
        return userService.loadUserByUsername(phone);
    }

    public SysUserEntity createDefaultUserWithPhone(String phone) {
        SysUserEntity entity = new SysUserEntity();
        String username = Constants.USER_NAME_PREFIX + RandomUtil.randomString(6);
        entity.setRoleId(3L);
        entity.setUsername(username);
        entity.setPassword(Constants.USER_INIT_PASSWORD);
        entity.setNickname(username);
        entity.setPhone(phone);
        userService.save(entity);
        return entity;
    }

    public CurrentLoginUserDTO getLoginUserInfo(Long id) {
        SysUserEntity entity = userService.loadById(id);
        String roleKey = roleService.loadById(entity.getRoleId()).getRoleKey();
        return new CurrentLoginUserDTO(entity, roleKey);
    }
}
