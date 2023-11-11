package com.rookie.enums.common;

import com.rookie.enums.BasicEnum;

/**
 * @author yayee
 */
public enum BusinessTypeEnum implements BasicEnum<Integer> {

    /**
     * 操作类型
     */
    OTHER(0, "其他操作"),
    ADD(1, "添加"),
    MODIFY(2, "修改"),
    DELETE(3, "删除"),
    GRANT(4, "授权"),
    EXPORT(5, "导出"),
    IMPORT(6, "导入"),
    FORCE_LOGOUT(7, "强退"),
    CLEAN(8, "清空"),
    ;

    private final int value;
    private final String description;

    BusinessTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }
}
