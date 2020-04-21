package com.muyun.springboot.repository;

import com.muyun.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author muyun
 * @date 2020/4/14
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
