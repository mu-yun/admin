package com.muyun.springboot.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.muyun.springboot.dto.UserChangeInfoDTO;
import com.muyun.springboot.dto.UserChangePasswordDTO;
import com.muyun.springboot.dto.UserDTO;
import com.muyun.springboot.dto.UserDetail;
import com.muyun.springboot.dto.UserInfoDTO;
import com.muyun.springboot.entity.Menu;
import com.muyun.springboot.entity.Role;
import com.muyun.springboot.entity.User;
import com.muyun.springboot.mapper.MenuMapper;
import com.muyun.springboot.mapper.UserMapper;
import com.muyun.springboot.model.Route;
import com.muyun.springboot.model.UserDetailInfo;
import com.muyun.springboot.repository.MenuRepository;
import com.muyun.springboot.repository.RoleRepository;
import com.muyun.springboot.repository.UserRepository;
import com.muyun.springboot.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final RoleRepository roleRepository;

    private final MenuRepository menuRepository;

    private final UserMapper userMapper;

    private final MenuMapper menuMapper;

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

    public UserDetailInfo getUserDetailInfo() {
        UserDetail userDetail = UserUtil.getCurrentUserDetail();
        Set<Menu> menus = (Set<Menu>) userDetail.getAuthorities();
        Set<String> authorities = Collections.emptySet();
        List<Route> routes = Collections.emptyList();
        if (menus != null && !menus.isEmpty()) {
            Map<Long, List<Menu>> parentToChildren = Maps.newHashMap();
            authorities = Sets.newHashSetWithExpectedSize(menus.size());
            final Set<String> finalAuthorities = authorities;
            menus.forEach(menu -> {
                if (menu.getType() != Menu.MenuType.BUTTON) {
                    Long parentId = menu.getParentId() == null ? 0L : menu.getParentId();
                    List<Menu> children = parentToChildren.get(parentId);
                    if (children == null) {
                        children = Lists.newArrayList();
                        parentToChildren.put(parentId, children);
                    }
                    children.add(menu);
                }
                if (Strings.isNotEmpty(menu.getAuthority())) {
                    finalAuthorities.add(menu.getAuthority());
                }
            });
            List<Menu> catalogs = parentToChildren.get(0L);
            if (catalogs != null) {
                routes = getRoutes(catalogs, parentToChildren);
            }
        }

        return userMapper.toUserDetailInfo(userDetail.getUser(), routes, authorities);
    }

    public List<Route> getRoutes(List<Menu> menus, Map<Long, List<Menu>> parentToChildren) {
        return menus.stream()
                .sorted(Comparator.comparingLong(Menu::getSequenceNumber).thenComparingLong(Menu::getId))
                .map(menu -> {
                    List<Route> children = null;
                    List<Menu> menuChildren = parentToChildren.get(menu.getId());
                    if (menuChildren != null) {
                        children = getRoutes(menuChildren, parentToChildren);
                    }
                    return menuMapper.toRoute(menu, children);
                })
                .collect(Collectors.toList());
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
        userRepository.save(user);
        user.setRoles(roleRepository.findAllById(userDTO.getRoles()).stream().collect(Collectors.toSet()));
        return user;
    }

    public void changeInfo(UserChangeInfoDTO userChangeInfoDTO) {
        User currentUser = UserUtil.getCurrentUser();

        update(currentUser.getId(), user -> {
            userMapper.updateUser(user, userChangeInfoDTO);
            userRepository.save(user);
            userMapper.updateUser(currentUser, user);
            return user;
        });
    }

    public void changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        User currentUser = UserUtil.getCurrentUser();

        update(currentUser.getId(), user -> {
            if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Old password is wrong");
            }
            user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
            userRepository.save(user);
            currentUser.setPassword(user.getPassword());
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
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw USERNAME_NOT_FOUND_EXCEPTION;
        }
        Set<Menu> authorities;
        if (user.getId().equals(ADMIN_ID)) {
            authorities = Sets.newHashSet(menuRepository.findAll());
        } else {
            authorities = user.getRoles().stream()
                    .map(Role::getMenus)
                    .flatMap(Set::stream).collect(Collectors.toSet());
        }

        return UserDetail.of(user, authorities);
    }

}
