package com.muyun.springboot.util;

import com.muyun.springboot.dto.UserDetail;
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
}
