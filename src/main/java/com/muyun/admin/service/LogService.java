package com.muyun.admin.service;

import com.muyun.admin.criteria.LogCriteria;
import com.muyun.admin.entity.Log;
import com.muyun.admin.mybatis.LogMapper;
import com.muyun.admin.repository.general.LogRepository;
import com.muyun.admin.vo.LogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author muyun
 * @date 2020/6/25
 */
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    private final LogMapper logMapper;

    public Page<LogVO> list(LogCriteria logCriteria, Pageable pageable) {
        List<LogVO> list = logMapper.findAll(logCriteria, pageable);
        long count = logMapper.count(logCriteria);
        return new PageImpl<>(list, pageable, count);
    }

    public void save(Log log) {
        logRepository.save(log);
    }

}
