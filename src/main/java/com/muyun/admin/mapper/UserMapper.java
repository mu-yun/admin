package com.muyun.admin.mapper;

import com.muyun.admin.dto.UserChangeInfoDTO;
import com.muyun.admin.dto.UserDTO;
import com.muyun.admin.dto.UserInfoDTO;
import com.muyun.admin.entity.User;
import com.muyun.admin.model.Route;
import com.muyun.admin.model.UserDetailInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

/**
 * @author muyun
 * @date 2020/6/3
 */
@Mapper(config = MapStructConfig.class, uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "password", source = "password")
    User toUser(UserDTO userDTO, String password);

    User updateUser(@MappingTarget User user, UserInfoDTO userInfoDTO);

    void updateUser(@MappingTarget User user, UserChangeInfoDTO userBasicDTO);

    void updateUser(@MappingTarget User userTarget, User userSource);

    UserDetailInfo toUserDetailInfo(User user, List<Route> routes, Set<String> authorities);

}
