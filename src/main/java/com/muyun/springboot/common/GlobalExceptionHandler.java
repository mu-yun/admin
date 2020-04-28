package com.muyun.springboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author muyun
 * @date 2020/4/22
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> messages = e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());

        log.error("请求参数校验失败:{}", messages);
        return Response.error(messages);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Response<String> handleThrowable(NoHandlerFoundException e) {
        log.error("", e);
        return Response.error(e.getMessage());
    }

    /**
     * 处理访问controller方法无权限导致的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Response<String> handleException(AccessDeniedException e) {
        log.error("", e);
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e) {
        log.error("", e);
        return Response.error(e.getMessage());
    }

}
