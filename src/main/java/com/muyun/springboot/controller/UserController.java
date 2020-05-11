package com.muyun.springboot.controller;

import com.muyun.springboot.dto.UserDTO;
import com.muyun.springboot.dto.UserDetail;
import com.muyun.springboot.entity.User;
import com.muyun.springboot.service.UserService;
import com.muyun.springboot.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author muyun
 * @date 2020/4/14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/detail")
    public UserDetail getDetail() {
        return UserUtil.getCurrentUserDetail();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User save(@RequestBody @Valid UserDTO user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody UserDTO user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
