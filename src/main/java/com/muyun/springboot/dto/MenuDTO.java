package com.muyun.springboot.dto;

import com.muyun.springboot.entity.Menu;
import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * @author muyun
 * @date 2020/5/14
 */
@Data
public class MenuDTO {

    private Long parentId;
    private String name;
    private String icon;
    private String path;
    private String componentName;
    private String componentPath;
    private String url;
    private HttpMethod httpMethod;
    private String authority;
    private Menu.MenuType type;
    private boolean hidden;
    private Long sequenceNumber;
}
