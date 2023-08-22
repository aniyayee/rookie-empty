package com.rookie.orm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.domain.user.dto.SysUserDTO;
import com.rookie.domain.user.query.UserQuery;
import com.rookie.orm.entity.SysUserEntity;
import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author yayee
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    List<SysUserDTO> findList(UserQuery query);
}
