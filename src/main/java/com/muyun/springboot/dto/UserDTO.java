package com.muyun.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author muyun
 * @date 2020/4/22
 */
@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须为6-11个字符")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    private String password;

    private String name;

    private String phoneNumber;
}
