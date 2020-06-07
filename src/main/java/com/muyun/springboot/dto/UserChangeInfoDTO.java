package com.muyun.springboot.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author muyun
 * @date 2020/6/6
 */
@Data
public class UserChangeInfoDTO {

    private String name;

    @Pattern(regexp = "^1[0-9]{10}$", message = "Incorrect phone number")
    private String phoneNumber;
}
