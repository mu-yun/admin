package com.muyun.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author muyun
 * @date 2020/4/22
 */
@Data
public class UserDTO extends UserInfoDTO {

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must be alphanumeric")
    @Size(min = 6, max = 16, message = "Password length must be between 6 and 16")
    private String password;

}
