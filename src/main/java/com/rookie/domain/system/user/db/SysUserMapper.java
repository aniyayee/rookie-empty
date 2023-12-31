package com.rookie.domain.system.user.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.domain.system.user.dto.UserDTO;
import com.rookie.domain.system.user.query.UserQuery;
import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author yayee
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    List<UserDTO> findList(UserQuery query);
}
