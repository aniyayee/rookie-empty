package com.rookie.common.core.dto;

import com.rookie.common.exception.RookieRuntimeException;
import com.rookie.common.exception.error.ErrorCode;
import lombok.Builder;
import lombok.Data;

/**
 * @author yayee
 */
@Data
@Builder
public class ResponseDTO<T> {

    /**
     * response success, true -> OK.
     */
    private Boolean success;

    /**
     * response code, 200 -> OK.
     */
    private Integer code;

    /**
     * response message.
     */
    private String message;

    /**
     * response data.
     */
    private T data;

    public static <T> ResponseDTO<T> ok() {
        return build(null, Boolean.TRUE, ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message());
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return build(data, Boolean.TRUE, ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message());
    }

    public static <T> ResponseDTO<T> fail() {
        return build(null, Boolean.FALSE, ErrorCode.FAIL.code(), ErrorCode.FAIL.message());
    }

    public static <T> ResponseDTO<T> fail(T data) {
        return build(data, Boolean.FALSE, ErrorCode.FAIL.code(), ErrorCode.FAIL.message());
    }

    public static <T> ResponseDTO<T> fail(RookieRuntimeException exception) {
        return build(null, Boolean.FALSE, exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseDTO<T> fail(RookieRuntimeException exception, T data) {
        return build(data, Boolean.FALSE, exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseDTO<T> build(T data, Boolean success, Integer code, String msg) {
        return ResponseDTO.<T>builder().data(data).message(msg).code(code).success(success)
            .build();
    }
}
