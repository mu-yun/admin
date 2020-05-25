package com.muyun.springboot.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * 定义entity基础字段。
 * <p>使用jpa的<code>AuditingEntityListener</code>为created和updated相关字段赋值，需要使用<code>@EnableJpaAuditing</code>激活配置
 *
 * @author muyun
 * @date 2020/4/16
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    public static final String WHERE_CLAUSE = "deleted = false";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedDate
    private Date updatedDate;

    @LastModifiedBy
    private Long updatedBy;

    /**
     * 逻辑删除(不显示该字段).
     * <p>使用基础类型，默认值为false
     */
    @JsonIgnore
    private boolean deleted;

}
