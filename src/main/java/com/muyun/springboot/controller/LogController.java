package com.muyun.springboot.controller;

import com.muyun.springboot.criteria.LogCriteria;
import com.muyun.springboot.service.LogService;
import com.muyun.springboot.vo.LogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muyun
 * @date 2020/5/27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    @GetMapping
    public Page<LogVO> list(LogCriteria logCriteria, @PageableDefault Pageable pageable) {
        return logService.list(logCriteria, pageable);
    }

}
