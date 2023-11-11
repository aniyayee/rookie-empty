package com.rookie.enums.common;

import com.rookie.enums.BasicEnum;

/**
 * @author yayee
 */
public enum OperationStatusEnum implements BasicEnum<Integer> {

    /**
     * 操作状态
     */
    SUCCESS(1, "成功"),
    FAIL(0, "失败"),
    ;

    private final int value;
    private final String description;

    OperationStatusEnum(int value, String description) {
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

