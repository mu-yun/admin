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
public class Response<T> {

    private static final Response EMPTY_DATA_SUCCESS_RESPONSE = new Response<>(200, null, null);

    private static final Response EMPTY_DATA_ERROR_RESPONSE = new Response<>(400, null, null);

    private final int code;

    private final String message;

    private final T data;

    public static <T> Response<T> success(T data) {
        return data == null ? EMPTY_DATA_SUCCESS_RESPONSE : new Response(200, null, data);
    }

    public static <T> Response<T> error(T data) {
        return data == null ? EMPTY_DATA_ERROR_RESPONSE : new Response(400, null, data);
    }
}
