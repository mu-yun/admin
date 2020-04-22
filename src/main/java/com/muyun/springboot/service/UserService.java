package com.muyun.springboot.service;

import com.muyun.springboot.dto.UserDTO;
import com.muyun.springboot.entity.User;
import com.muyun.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author muyun
 * @date 2020/4/14
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User get(Long id) {
        return userRepository.getOne(id);
    }

    public User save(UserDTO userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

    public User update(User userDto) {
        User user = get(userDto.getId());
        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
