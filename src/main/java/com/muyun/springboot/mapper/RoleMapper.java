package com.muyun.springboot.mapper;

import com.muyun.springboot.dto.RoleDTO;
import com.muyun.springboot.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author muyun
 * @date 2020/5/26
 */
@Mapper(config = MapStructConfig.class, uses = MenuMapper.class)
public interface RoleMapper {

    void updateRole(@MappingTarget Role role, RoleDTO roleDTO);

    Role toRole(RoleDTO roleDTO);

}
