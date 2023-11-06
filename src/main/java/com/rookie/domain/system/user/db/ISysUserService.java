package com.rookie.domain.system.user.db;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author yayee
 */
public interface ISysUserService extends IService<SysUserEntity> {

    boolean isUserNameDuplicated(Long id, String username);

    boolean isPhoneDuplicated(Long id, String phone);

    SysUserEntity loadById(Long id);

    SysUserEntity loadUserByUsername(String phone);
}
