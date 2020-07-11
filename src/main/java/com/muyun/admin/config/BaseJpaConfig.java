package com.muyun.admin.config;

import com.muyun.admin.jpa.BaseJpaRepositoryFactoryBean;
import com.muyun.admin.jpa.BaseSimpleJpaRepository;
import com.muyun.admin.repository.base.BasePackageMarkerInterface;
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
