package com.muyun.springboot.exception;

/**
 * @author muyun
 * @date 2020/7/1
 */
public class DataNotFoundException extends RuntimeException {

    public static final DataNotFoundException DATA_NOT_FOUND_EXCEPTION = new DataNotFoundException();

    private DataNotFoundException() {
        super("Data not found");
    }
}
