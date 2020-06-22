package com.muyun.springboot.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author muyun
 * @date 2020/6/7
 */
@Getter
@RequiredArgsConstructor
public enum ResponseStatus {

    SUCCESS(2000, "Success"),
    VALIDATION_ERROR(3001, "Input validation error"),
    AUTHENTICATION_FAIL(4001, "Username or password is incorrect"),
    UNAUTHORIZED(4002, "Please login"),
    FORBIDDEN(4003, "No permission"),
    NOT_FOUND(4004, "Request not found, please contact the administrator"),
    INTERNAL_ERROR(5000, "Internal server error, please contact the administrator");

    private final int code;

    private final String message;
}
