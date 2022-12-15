package com.alexey.shop.repository.specification;

import com.alexey.shop.model.User;

public class UserSpecification {

    public static org.springframework.data.jpa.domain.Specification<User> equalId(Long id){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

    public static org.springframework.data.jpa.domain.Specification<User> likeLogin(String loginPart){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("login")), String.format("%%%s%%", loginPart.toUpperCase())));
    }

//    public static org.springframework.data.jpa.domain.Specification<User> greaterThanOrEqualToScore(Integer cost){
//        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost));
//    }

}
