package com.muyun.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author muyun
 * @date 2020/6/6
 */
@Data
public class UserChangePasswordDTO {

    @NotEmpty(message = "Old password is required")
    private String oldPassword;

    @NotNull(message = "New password is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "New password must be alphanumeric")
    @Size(min = 6, max = 16, message = "New password length must be between 6 and 16")
    private String newPassword;

}
