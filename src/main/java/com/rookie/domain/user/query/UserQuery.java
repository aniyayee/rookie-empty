package com.rookie.domain.user.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rookie.common.core.page.AbstractPageQuery;
import com.rookie.domain.user.db.SysUserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends AbstractPageQuery<SysUserEntity> {

    private String username;
    private String nickname;
    private String email;
    private String phone;

    @Override
    public QueryWrapper<SysUserEntity> addQueryCondition() {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(username), "username", username)
            .eq(StringUtils.isNotEmpty(nickname), "nickname", nickname)
            .eq(StringUtils.isNotEmpty(email), "email", email)
            .eq(StringUtils.isNotEmpty(phone), "phone", phone);

        this.orderColumn = "user_id";
        this.orderDirection = "descending";
        return queryWrapper;
    }
}
