package com.muyun.springboot.entity;

import com.muyun.springboot.common.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author muyun
 * @date 2020/4/14
 */
@Data
@Entity
@DynamicUpdate
public class User extends BaseEntity {

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

}
