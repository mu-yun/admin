package com.muyun.springboot.controller;

import com.muyun.springboot.dto.MenuDTO;
import com.muyun.springboot.entity.Menu;
import com.muyun.springboot.service.MenuService;
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

    @GetMapping("/{id}")
    public Menu get(@PathVariable Long id) {
        return menuService.get(id);
    }

    @GetMapping
    public List<Menu> list() {
        return menuService.list(null);
    }

    @GetMapping("/{id}/menu")
    public List<Menu> list(@PathVariable Long id) {
        return menuService.list(id);
    }

    @PostMapping
    public Menu save(@RequestBody MenuDTO menuDTO) {
        return menuService.save(menuDTO);
    }

    @PutMapping("/{id}")
    public Menu update(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        return menuService.update(id, menuDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
