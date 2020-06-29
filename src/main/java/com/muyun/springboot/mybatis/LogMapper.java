package com.muyun.springboot.mybatis;

import com.muyun.springboot.criteria.LogCriteria;
import com.muyun.springboot.vo.LogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author muyun
 * @date 2020/6/27
 */
@Mapper
public interface LogMapper {

    List<LogVO> findAll(@Param("logCriteria") LogCriteria logCriteria, @Param("pageable") Pageable pageable);

    long count(@Param("logCriteria") LogCriteria logCriteria);

}