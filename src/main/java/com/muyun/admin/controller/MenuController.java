package com.muyun.admin.controller;

import com.muyun.admin.dto.MenuDTO;
import com.muyun.admin.service.MenuService;
import com.muyun.admin.vo.MenuTreeVO;
import com.muyun.admin.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author muyun
 * @date 2020/5/14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping(value = {"/tree", "/tree/{id}"})
    public List<MenuTreeVO> listTree(@PathVariable(required = false) Long id) {
        return menuService.listTree(id);
    }

    @GetMapping(value = {"", "/{id}"})
    public List<MenuVO> list(@PathVariable(required = false) Long id) {
        return menuService.list(id);
    }

    @PostMapping
    public MenuVO save(@RequestBody MenuDTO menuDTO) {
        return menuService.save(menuDTO);
    }

    @PutMapping("/{id}")
    public MenuVO update(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        return menuService.update(id, menuDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
