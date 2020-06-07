package com.muyun.springboot.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 统一返回数据
 *
 * @author muyun
 * @date 2020/4/21
 */
@Getter
@RequiredArgsConstructor
public class ResponseData<T> {

    private static final ResponseData EMPTY_DATA_SUCCESS_RESPONSE = of(ResponseStatus.SUCCESS);

    private final int code;

    private final String message;

    private final T data;

    public static <T> ResponseData<T> of(ResponseStatus status, T data) {
        return new ResponseData<>(status.getCode(), status.getMessage(), data);
    }

    public static <T> ResponseData<T> of(ResponseStatus status) {
        return of(status, null);
    }

    public static <T> ResponseData<T> success(T data) {
        return of(ResponseStatus.SUCCESS, data);
    }

    public static <T> ResponseData<T> success() {
        return EMPTY_DATA_SUCCESS_RESPONSE;
    }
}
