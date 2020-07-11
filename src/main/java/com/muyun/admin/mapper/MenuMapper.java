package com.muyun.admin.mapper;

import com.muyun.admin.dto.MenuDTO;
import com.muyun.admin.dto.MenuTreeDTO;
import com.muyun.admin.entity.Menu;
import com.muyun.admin.model.Route;
import com.muyun.admin.vo.MenuTreeVO;
import com.muyun.admin.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/5/26
 */
@Mapper(config = MapStructConfig.class)
public interface MenuMapper {

    Menu idToMenu(Long id);

    Set<Menu> idsToMenus(Set<Long> ids);

    Menu toMenu(MenuDTO menuDTO);

    void updateMenu(@MappingTarget Menu menu, MenuDTO menuDTO);

    //avoid infinite loop
    @Mapping(target = "roles", ignore = true)
    MenuVO toMenuVO(Menu menu, boolean hasChildren);

    MenuTreeVO toMenuTreeVO(MenuTreeDTO menuTreeDTO, boolean childless);

    Route toRoute(Menu menu, List<Route> children);
}
