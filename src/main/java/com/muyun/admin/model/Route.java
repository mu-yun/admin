package com.muyun.admin.model;

import com.muyun.admin.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author muyun
 * @date 2020/6/21
 */
@Data
public class Route {

    private String name;

    private String icon;

    private String path;

    private String componentName;

    private String componentPath;

    private String authority;

    private Menu.MenuType type;

    private List<Route> children;

}
