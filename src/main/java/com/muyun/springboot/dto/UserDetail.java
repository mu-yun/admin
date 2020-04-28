package com.muyun.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muyun.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/4/27
 */
@Data
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final Set<? extends GrantedAuthority> authorities;

    public static UserDetail fromUser(User user) {
        return Objects.isNull(user) ? null : new UserDetail(user.getId(), user.getUsername(), user.getPassword(), null);
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

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
