package com.muyun.springboot.service;

import com.muyun.springboot.dto.UserChangeInfoDTO;
import com.muyun.springboot.dto.UserChangePasswordDTO;
import com.muyun.springboot.dto.UserDTO;
import com.muyun.springboot.dto.UserDetail;
import com.muyun.springboot.dto.UserInfoDTO;
import com.muyun.springboot.entity.User;
import com.muyun.springboot.mapper.UserMapper;
import com.muyun.springboot.repository.UserRepository;
import com.muyun.springboot.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author muyun
 * @date 2020/4/14
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final Long ADMIN_ID = 1L;

    private static final UsernameNotFoundException USERNAME_NOT_FOUND_EXCEPTION = new UsernameNotFoundException("Username not found");

    private static final ExampleMatcher MATCHER = ExampleMatcher.matching()
            .withMatcher("name", match -> match.contains().ignoreCase())
            .withMatcher("username", matcher -> matcher.contains().ignoreCase());

    private static final Specification<User> ID_SPEC = (root, query, criteriaBuilder)
            -> criteriaBuilder.greaterThan(root.get("id"), ADMIN_ID);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (!userRepository.existsById(ADMIN_ID)) {
            User user = new User();
            user.setUsername("admin");
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setCreatedBy(ADMIN_ID);
            user.setUpdatedBy(ADMIN_ID);
            userRepository.save(user);
        }
    }


    public Page<User> list(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(ID_SPEC.and(spec), pageable);
    }

    public Page<User> list(User user, Pageable pageable) {
        Example example = Example.of(user, MATCHER);
        return userRepository.findAll(example, pageable);
    }

    public User save(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO, passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public void changeInfo(UserChangeInfoDTO userChangeInfoDTO) {
        UserDetail userDetail = UserUtil.getCurrentUserDetail();

        update(userDetail.getId(), user -> {
            userMapper.updateUser(user, userChangeInfoDTO);
            userRepository.save(user);
            userMapper.updateUserDetail(userDetail, userChangeInfoDTO);
            return user;
        });
    }

    public void changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        UserDetail userDetail = UserUtil.getCurrentUserDetail();

        update(userDetail.getId(), user -> {
            if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Old password is wrong");
            }
            user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
            userRepository.save(user);
            userDetail.setPassword(user.getPassword());
            return user;
        });
    }

    public User update(Long id, UserInfoDTO userDTO) {
        return update(id, user -> {
            userMapper.updateUser(user, userDTO);
            return userRepository.save(user);
        });
    }

    public User updatePassword(Long id, String password) {
        return update(id, user -> {
            user.setPassword(passwordEncoder.encode(password));
            return userRepository.save(user);
        });
    }

    @Transactional
    User update(Long id, Function<User, User> mapper) {
        return userRepository.findById(id)
                .map(mapper).orElseThrow(() -> new RuntimeException("修改的用户不存在"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw USERNAME_NOT_FOUND_EXCEPTION;
        }
        return UserDetail.fromUser(user);
    }

}
