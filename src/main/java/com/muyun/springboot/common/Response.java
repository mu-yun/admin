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

    private final int code;

    private final String message;

    private final T data;

    public static <T> Response<T> success(T data) {
        if (data == null) {
            return EMPTY_DATA_SUCCESS_RESPONSE;
        }
        Response response = new Response(200, null, data);
        return response;
    }
}
