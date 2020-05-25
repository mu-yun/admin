package com.muyun.springboot.entity;

import com.muyun.springboot.common.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;


/**
 * @author muyun
 * @date 2020/5/13
 */
@Data
@Entity
@DynamicUpdate
@Where(clause = BaseEntity.WHERE_CLAUSE)
@Table(indexes = @Index(columnList = "parentId"))
public class Menu extends BaseEntity implements GrantedAuthority {

    private Long parentId;

    @Column(nullable = false)
    private String name;

    private String icon;

    private String path;

    private String componentName;

    private String componentPath;

    private String url;

    private HttpMethod httpMethod;

    private String authority;

    @Column(nullable = false)
    private MenuType type;

    private boolean hidden;

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
