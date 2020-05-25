package com.muyun.springboot;

import com.muyun.springboot.jpa.BaseJpaRepositoryFactoryBean;
import com.muyun.springboot.jpa.BaseSimpleJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author muyun
 * @date 2020/03/14
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseJpaRepositoryFactoryBean.class, repositoryBaseClass = BaseSimpleJpaRepository.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
