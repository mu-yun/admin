package com.muyun.springboot.controller;

import com.muyun.springboot.dto.UserDetail;
import com.muyun.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muyun
 * @date 2020/4/28
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/login")
    public UserDetail login(@RequestParam String username, @RequestParam String password) {
        return userService.getUserDetailFromCacheByUsername(username);
    }

}
