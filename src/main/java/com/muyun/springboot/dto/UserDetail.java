package com.muyun.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muyun.springboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/4/27
 */
@Getter
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private final User user;

    private Set<? extends GrantedAuthority> authorities;

    public static UserDetail of(User user, Set<? extends GrantedAuthority> authorities) {
        return new UserDetail(user, authorities);
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return !user.getDeleted();
    }
}
