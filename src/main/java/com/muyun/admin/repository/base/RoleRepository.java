package com.muyun.admin.repository.base;

import com.muyun.admin.entity.Role;
import com.muyun.admin.vo.RoleVO;
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

    @Query("select new com.muyun.admin.vo.RoleVO(id,name) from Role")
    List<RoleVO> findAllRole();
}
