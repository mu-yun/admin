package com.muyun.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author muyun
 * @date 2020/4/22
 */
@Configuration
public class SpringMvcConfig {

    /**
     * log request info
     *
     * @return
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter requestLoggingFilter = new CommonsRequestLoggingFilter();
        requestLoggingFilter.setIncludeClientInfo(true);
        requestLoggingFilter.setIncludeQueryString(true);
        return requestLoggingFilter;
    }
}
