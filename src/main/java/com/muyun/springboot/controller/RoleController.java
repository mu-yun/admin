package com.muyun.springboot.controller;

import com.muyun.springboot.dto.RoleDTO;
import com.muyun.springboot.entity.Role;
import com.muyun.springboot.service.RoleService;
import com.muyun.springboot.vo.RoleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/5/27
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}/menus")
    public Set<Long> getMenus(@PathVariable Long id) {
        return roleService.getMenus(id);
    }

    @GetMapping("/all")
    public List<RoleVO> listAll() {
        return roleService.listAll();
    }

    @GetMapping
    public Page<Role> list(String name, @PageableDefault Pageable pageable) {
        return roleService.list(name, pageable);
    }

    @PostMapping
    public Role save(@RequestBody RoleDTO roleDTO) {
        return roleService.save(roleDTO);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        return roleService.update(id,roleDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}
