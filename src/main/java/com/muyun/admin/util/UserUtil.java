package com.muyun.admin.util;

import com.muyun.admin.dto.UserDetail;
import com.muyun.admin.entity.User;
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
