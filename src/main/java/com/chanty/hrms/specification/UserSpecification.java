package com.chanty.hrms.specification;

import com.chanty.hrms.model.setup.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecification {
    public static Specification<User> firstNameLike(String search) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("firstName")), "%" + search.toLowerCase() + "%");

    }
    public static Specification<User> lastNameLike(String search) {
        return (root,query,cb) -> cb.like(cb.lower(root.get("lastName")), "%" + search.toLowerCase() + "%");
    }
    public static Specification<User> usernameLike(String search) {
        return (root,query,cb) -> cb.like(root.get("username"), "%" + search + "%");
    }
    public static Specification<User> emailLike(String search) {
        return (root,query,cb) -> cb.like(cb.lower(root.get("email")), "%" + search.toLowerCase() + "%");
    }

    public static Specification<User> getContent(String search){
        if (StringUtils.isEmpty(search) || StringUtils.isBlank(search)) {
            return null;
        }
        return Specification.where(firstNameLike(search)).or(lastNameLike(search).or(usernameLike(search)).or(emailLike(search)));
    }
}
