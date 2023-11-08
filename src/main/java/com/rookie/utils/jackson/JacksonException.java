package com.rookie.utils.jackson;

/**
 * @author yayee
 */
public class JacksonException extends RuntimeException {

    public JacksonException(String message, Exception e) {
        super(message, e);
    }
}
