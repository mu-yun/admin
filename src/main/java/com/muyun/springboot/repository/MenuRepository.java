package com.muyun.springboot.repository;

import com.muyun.springboot.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author muyun
 * @date 2020/5/14
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByParentId(Long id);

    long countByParentId(Long id);

    void deleteAllByParentId(Long id);
}
