package com.muyun.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muyun.springboot.jpa.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/5/26
 */
@Data
@Entity
@DynamicUpdate
@Where(clause = BaseEntity.WHERE_CLAUSE)
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany
    private Set<Menu> menus = new HashSet<>();

}
