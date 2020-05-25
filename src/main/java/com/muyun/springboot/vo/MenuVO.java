package com.muyun.springboot.vo;

import com.muyun.springboot.entity.Menu;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author muyun
 * @date 2020/5/21
 */
@Data
public class MenuVO extends Menu {

    public static MenuVO of(Menu menu, boolean hasChildren) {
        MenuVO menuVo = new MenuVO();
        BeanUtils.copyProperties(menu, menuVo);
        menuVo.hasChildren = hasChildren;
        return menuVo;
    }

    private boolean hasChildren;
}
