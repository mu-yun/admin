package com.muyun.springboot.config;

import com.muyun.springboot.jpa.BaseJpaRepositoryFactoryBean;
import com.muyun.springboot.jpa.BaseSimpleJpaRepository;
import com.muyun.springboot.repository.base.BasePackageMarkerInterface;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author muyun
 * @date 2020/6/25
 */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class,
        repositoryBaseClass = BaseSimpleJpaRepository.class,
        basePackageClasses = BasePackageMarkerInterface.class)
public class BaseJpaConfig {

}
