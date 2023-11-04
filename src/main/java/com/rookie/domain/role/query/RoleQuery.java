package com.rookie.domain.role.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rookie.common.core.page.AbstractPageQuery;
import com.rookie.domain.role.db.SysRoleEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQuery extends AbstractPageQuery<SysRoleEntity> {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色权限字符串")
    private String roleKey;

    @Override
    public QueryWrapper<SysRoleEntity> addQueryCondition() {
        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(roleName), "role_name", roleName)
            .eq(StringUtils.isNotEmpty(roleKey), "role_key", roleKey);

        this.orderColumn = "role_id";
        this.orderDirection = "descending";
        return queryWrapper;
    }
}
