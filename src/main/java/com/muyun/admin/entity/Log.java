package com.muyun.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author muyun
 * @date 2020/6/25
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String operation;

    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @CreatedBy
    private Long createdBy;
}
