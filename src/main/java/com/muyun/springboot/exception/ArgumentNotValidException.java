package com.muyun.springboot.exception;

import lombok.Getter;

import java.util.List;

/**
 * @author muyun
 * @date 2020/6/13
 */
@Getter
public class ArgumentNotValidException extends RuntimeException {

    private final List<String> messages;

    public ArgumentNotValidException(List<String> messages) {
        super("Argument not valid");
        this.messages = messages;
    }
}
