package com.muyun.springboot.config;

import com.muyun.springboot.repository.general.GeneralPackageMarkerInterface;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author muyun
 * @date 2020/6/25
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = GeneralPackageMarkerInterface.class)
public class GeneralJpaConfig {

}
