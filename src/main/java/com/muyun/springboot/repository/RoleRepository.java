package com.muyun.springboot.repository;

import com.muyun.springboot.entity.Role;
import com.muyun.springboot.vo.RoleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/5/26
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select menus_id from role_menus where roles_id=:id", nativeQuery = true)
    Set<Long> getRoleMenusId(Long id);

    @Query("select new com.muyun.springboot.vo.RoleVO(id,name) from Role")
    List<RoleVO> findAllRole();
}
