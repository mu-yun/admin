package com.muyun.springboot.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author muyun
 * @date 2020/4/15
 */
@Configuration
public class JacksonConfig {

    /**
     * jackson与hibernate lazy load 冲突解决办法
     *
     * @return
     */
    @Bean
    public Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }
}
