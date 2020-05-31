package com.muyun.springboot.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * @author muyun
 * @date 2020/5/30
 */
@MapperConfig(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface MapStructConfig {

}
