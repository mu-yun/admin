package com.muyun.admin.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author muyun
 * @date 2020/5/30
 */
@Data
public class RoleDTO {

    private String name;

    private String description;

    private Set<Long> menus;
}
