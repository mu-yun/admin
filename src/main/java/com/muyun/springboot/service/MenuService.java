package com.muyun.springboot.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.muyun.springboot.dto.MenuDTO;
import com.muyun.springboot.entity.Menu;
import com.muyun.springboot.mapper.MenuMapper;
import com.muyun.springboot.repository.base.MenuRepository;
import com.muyun.springboot.vo.MenuTreeVO;
import com.muyun.springboot.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author muyun
 * @date 2020/5/14
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final LoadingCache<Long, Boolean> MENU_HAS_CHILDREN_CACHE = CacheBuilder.newBuilder()
            .build(new CacheLoader<Long, Boolean>() {
                @Override
                public Boolean load(Long key) {
                    return menuRepository.countByParentId(key) != 0;
                }
            });

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public List<MenuTreeVO> listTree(Long parentId) {
        return menuRepository.findByParentId(parentId)
                .stream()
                .map(m -> menuMapper.toMenuTreeVO(m, !MENU_HAS_CHILDREN_CACHE.getUnchecked(m.getId())))
                .collect(Collectors.toList());
    }

    public List<MenuVO> list(Long parentId) {
        return menuRepository.findAllByParentId(parentId)
                .stream()
                .map(m -> menuMapper.toMenuVO(m, MENU_HAS_CHILDREN_CACHE.getUnchecked(m.getId())))
                .collect(Collectors.toList());
    }

    public MenuVO save(MenuDTO menuDTO) {
        Menu menu = menuMapper.toMenu(menuDTO);
        menuRepository.save(menu);

        putCache(menu.getParentId());
        return menuMapper.toMenuVO(menu, false);
    }

    @Transactional
    public MenuVO update(Long id, MenuDTO menuDTO) {
        return menuRepository.findById(id)
                .map(menu -> {
                    Long oldParentId = menu.getParentId();

                    menuMapper.updateMenu(menu, menuDTO);
                    menuRepository.save(menu);

                    if (!Objects.equals(oldParentId, menu.getParentId())) {
                        invalidateCache(oldParentId);
                        putCache(menu.getParentId());
                    }
                    return menuMapper.toMenuVO(menu, MENU_HAS_CHILDREN_CACHE.getUnchecked(id));
                })
                .orElseThrow(() -> new RuntimeException("修改的菜单不存在"));
    }

    @Transactional
    public void delete(Long id) {
        menuRepository.findById(id)
                .map(menu -> {
                    menuRepository.delete(menu);
                    menuRepository.deleteAllByParentId(id);

                    invalidateCache(menu.getParentId());
                    invalidateCache(menu.getId());
                    return menu;
                })
                .orElseThrow(() -> new RuntimeException("删除的菜单不存在"));
    }

    private void invalidateCache(Long id) {
        if (Objects.nonNull(id)) {
            MENU_HAS_CHILDREN_CACHE.invalidate(id);
        }
    }

    private void putCache(Long id) {
        if (Objects.nonNull(id)) {
            MENU_HAS_CHILDREN_CACHE.put(id, Boolean.TRUE);
        }
    }

}
