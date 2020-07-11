package com.muyun.admin.repository.base;

import com.muyun.admin.dto.MenuTreeDTO;
import com.muyun.admin.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author muyun
 * @date 2020/5/14
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByParentId(Long id);

    @Query("select new com.muyun.admin.dto.MenuTreeDTO(id,name) from Menu where parentId=:id or(:id is null and parentId is null ) ")
    List<MenuTreeDTO> findByParentId(Long id);

    long countByParentId(Long id);

    void deleteAllByParentId(Long id);
}
