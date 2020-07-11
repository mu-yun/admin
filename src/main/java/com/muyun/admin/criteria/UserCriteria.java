package com.muyun.admin.criteria;

import com.google.common.base.Strings;
import com.muyun.admin.entity.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author muyun
 * @date 2020/6/3
 */
@Data
public class UserCriteria {

    private String keyword;

    private UserSearchType type;

    public User getProbe() {
        User user = new User();
        if (type == UserSearchType.NAME) {
            user.setName(keyword);
        } else if (type == UserSearchType.USERNAME) {
            user.setUsername(keyword);
        }
        return user;
    }

    public Specification<User> getSpec() {
        if (Strings.isNullOrEmpty(keyword)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(type == UserSearchType.NAME ? "name" : "username"), "%" + keyword + "%");
    }

    public enum UserSearchType {
        NAME,
        USERNAME
    }
}
