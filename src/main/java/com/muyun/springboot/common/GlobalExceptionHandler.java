package com.muyun.springboot.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author muyun
 * @date 2020/4/22
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.error(e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList()));
    }

    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e) {
        return Response.error(e.getMessage());
    }
}
