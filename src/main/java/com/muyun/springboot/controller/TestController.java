package com.muyun.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final String TEST = "test";

    @GetMapping("test")
    public String test() {
        return TEST;
    }

}
