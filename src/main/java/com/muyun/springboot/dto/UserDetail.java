package com.muyun.springboot.dto;

import com.muyun.springboot.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/4/27
 */
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private final String username;

    private final String password;

    private final Set<? extends GrantedAuthority> authorities;

    public static UserDetail fromUser(User user) {
        return Objects.isNull(user) ? null : new UserDetail(user.getUsername(), user.getPassword(), null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
