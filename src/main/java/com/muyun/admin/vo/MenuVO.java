package com.muyun.admin.vo;

import com.muyun.admin.entity.Menu;
import lombok.Data;

/**
 * @author muyun
 * @date 2020/5/21
 */
@Data
public class MenuVO extends Menu {

    private boolean hasChildren;

}
