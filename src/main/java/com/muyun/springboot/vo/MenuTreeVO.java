package com.muyun.springboot.vo;

import com.muyun.springboot.dto.MenuTreeDTO;
import lombok.Data;

/**
 * @author muyun
 * @date 2020/5/29
 */
@Data
public class MenuTreeVO extends MenuTreeDTO {

    private boolean childless;

}


