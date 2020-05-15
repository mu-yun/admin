package com.muyun.springboot.entity;

import com.muyun.springboot.common.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author muyun
 * @date 2020/5/13
 */
@Data
@Entity
@DynamicUpdate
@Where(clause = BaseEntity.WHERE_CLAUSE)
public class Menu extends BaseEntity implements GrantedAuthority {

    private Long parentId;
    private String name;
    private String icon;
    private String path;
    private String componentPath;
    private String url;
    private HttpMethod httpMethod;
    private String authority;
    @Column(nullable = false)
    private MenuType menuType;
    private long sequenceNumber;

    @Override
    public String getAuthority() {
        return authority;
    }

    public enum MenuType {
        CATALOG,
        MENU,
        BUTTON
    }
}
