package com.muyun.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muyun.springboot.jpa.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/4/14
 */
@Data
@Entity
@DynamicUpdate
@Where(clause = BaseEntity.WHERE_CLAUSE)
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String name;

    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
