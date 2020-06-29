package com.muyun.springboot.criteria;

import lombok.Data;

import java.util.Date;

/**
 * @author muyun
 * @date 2020/6/29
 */
@Data
public class LogCriteria extends UserCriteria {

    private String operation;

    private Date startDate;

    private Date endDate;
}
