package com.muyun.springboot;

import com.muyun.springboot.common.BaseJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author muyun
 * @date 2020/03/14
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepository.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
