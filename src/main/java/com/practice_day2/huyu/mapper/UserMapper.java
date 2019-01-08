package com.practice_day2.huyu.mapper;

import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse userToUserRespone(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
    }
}
