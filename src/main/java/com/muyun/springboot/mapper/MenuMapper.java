package com.muyun.springboot.mapper;

import com.muyun.springboot.dto.MenuDTO;
import com.muyun.springboot.entity.Menu;
import com.muyun.springboot.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author muyun
 * @date 2020/5/26
 */
@Mapper(componentModel = "spring")
public interface MenuMapper {

    Menu toMenu(MenuDTO menuDTO);

    void updateMenu(@MappingTarget Menu menu, MenuDTO menuDTO);

    MenuVO toMenuVo(Menu menu, boolean hasChildren);
}
