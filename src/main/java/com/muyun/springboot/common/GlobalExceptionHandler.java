package com.muyun.springboot.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
    public ResponseData<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> messages = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        log.error("MethodArgumentNotValid:{}", messages, e);
        return ResponseData.of(ResponseStatus.VALIDATION_ERROR, messages);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseData<String> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return response(ResponseStatus.NOT_FOUND, e);
    }

    /**
     * 处理访问controller方法无权限导致的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseData<String> handleAccessDeniedException(AccessDeniedException e) {
        return response(ResponseStatus.FORBIDDEN, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseData<String> handleException(Exception e) {
        return response(ResponseStatus.INTERNAL_ERROR, e);
    }

    private ResponseData<String> response(ResponseStatus status, Exception e) {
        log.error("", e);
        return ResponseData.of(status);
    }

}
