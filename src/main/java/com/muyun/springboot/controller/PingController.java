package com.muyun.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muyun
 * @date 2020/4/13
 */
@RestController
public class PingController {

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }

}
