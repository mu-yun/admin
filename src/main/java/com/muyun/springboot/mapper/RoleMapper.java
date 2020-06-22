package com.muyun.springboot.mapper;

import com.muyun.springboot.dto.RoleDTO;
import com.muyun.springboot.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author muyun
 * @date 2020/5/26
 */
@Mapper(config = MapStructConfig.class, uses = MenuMapper.class)
public interface RoleMapper {

    Role idToRole(Long id);

    Set<Role> idsToRoles(Set<Long> ids);

    void updateRole(@MappingTarget Role role, RoleDTO roleDTO);

    Role toRole(RoleDTO roleDTO);

    default Set<String> rolesToNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

}
