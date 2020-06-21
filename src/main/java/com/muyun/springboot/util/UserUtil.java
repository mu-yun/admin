package com.muyun.springboot.util;

import com.muyun.springboot.dto.UserDetail;
import com.muyun.springboot.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author muyun
 * @date 2020/5/11
 */
public final class UserUtil {

    private UserUtil() {

    }

    public static UserDetail getCurrentUserDetail() {
        return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static User getCurrentUser() {
        return getCurrentUserDetail().getUser();
    }
}
