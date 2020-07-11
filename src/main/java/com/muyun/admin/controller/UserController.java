package com.muyun.admin.controller;

import com.muyun.admin.criteria.UserCriteria;
import com.muyun.admin.dto.UserChangeInfoDTO;
import com.muyun.admin.dto.UserChangePasswordDTO;
import com.muyun.admin.dto.UserDTO;
import com.muyun.admin.dto.UserInfoDTO;
import com.muyun.admin.dto.UserPasswordDTO;
import com.muyun.admin.entity.User;
import com.muyun.admin.model.UserDetailInfo;
import com.muyun.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public UserDetailInfo getDetail() {
        return userService.getUserDetailInfo();
    }

    @PutMapping
    public void changeInfo(@RequestBody @Valid UserChangeInfoDTO userChangeInfoDTO) {
        userService.changeInfo(userChangeInfoDTO);
    }

    @PutMapping("/password")
    public void changePassword(@RequestBody @Valid UserChangePasswordDTO userChangePasswordDTO) {
        userService.changePassword(userChangePasswordDTO);
    }

    @GetMapping
    public Page<User> list(UserCriteria criteria, @PageableDefault Pageable pageable) {
        return userService.list(criteria.getSpec(), pageable);
    }

    @PostMapping
    public User save(@RequestBody @Valid UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("/{id}") //TODO forbid update admin
    public User update(@PathVariable Long id, @RequestBody @Valid UserInfoDTO userInfoDTO) {
        return userService.update(id, userInfoDTO);
    }

    @PutMapping("/{id}/password")//TODO forbid update admin
    public User updatePassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDTO userPasswordDTO) {
        return userService.updatePassword(id, userPasswordDTO.getPassword());
    }

    @DeleteMapping("/{id}")//TODO forbid delete admin
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
