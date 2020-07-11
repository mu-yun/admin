package com.muyun.admin.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author muyun
 * @date 2020/6/22
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserLoginInfo {

    private final String token;

    private final UserDetailInfo userInfo;
}
