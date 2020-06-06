package com.muyun.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/6/3
 */
@Data
public class UserInfoDTO {

    @NotNull(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "Username must be alphanumeric and start with a letter")
    @Size(min = 5, max = 16, message = "Username length must be between 5 and 16")
    private String username;

    private String name;

    @Pattern(regexp = "^1[0-9]{10}$", message = "Incorrect phone number")
    private String phoneNumber;

    private Set<Long> roles;
}
