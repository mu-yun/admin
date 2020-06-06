package com.muyun.springboot.mapper;

import com.muyun.springboot.dto.UserDTO;
import com.muyun.springboot.dto.UserInfoDTO;
import com.muyun.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author muyun
 * @date 2020/6/3
 */
@Mapper(config = MapStructConfig.class, uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "password", source = "password")
    User toUser(UserDTO userDTO, String password);

    User updateUser(@MappingTarget User user, UserInfoDTO userInfoDTO);

}
