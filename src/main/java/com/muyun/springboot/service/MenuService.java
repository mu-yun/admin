package com.muyun.springboot.service;

import com.muyun.springboot.dto.MenuDTO;
import com.muyun.springboot.entity.Menu;
import com.muyun.springboot.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author muyun
 * @date 2020/5/14
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu get(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("菜单不存在"));
    }

    public List<Menu> list(Long parentId) {
        return menuRepository.findAllByParentId(parentId);
    }

    public Menu save(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        return menuRepository.save(menu);
    }

    public Menu update(Long id, MenuDTO menuDTO) {
        return menuRepository.findById(id)
                .map(menu -> {
                    BeanUtils.copyProperties(menuDTO, menu);
                    return menu;
                })
                .orElseThrow(() -> new RuntimeException("修改的菜单不存在"));
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

}
