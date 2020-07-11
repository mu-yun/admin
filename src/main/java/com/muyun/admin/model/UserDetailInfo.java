package com.muyun.admin.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/6/21
 */
@Data
public class UserDetailInfo {

    private String username;

    private String name;

    private String phoneNumber;

    private Set<String> roles;

    private List<Route> routes;

    private Set<String> authorities;
}
