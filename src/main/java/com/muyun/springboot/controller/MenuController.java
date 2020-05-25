package com.muyun.springboot.controller;

import com.muyun.springboot.dto.MenuDTO;
import com.muyun.springboot.service.MenuService;
import com.muyun.springboot.vo.MenuVO;
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

    @GetMapping
    public List<MenuVO> list() {
        return menuService.list(null);
    }

    @GetMapping("/{id}/menu")
    public List<MenuVO> list(@PathVariable Long id) {
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
