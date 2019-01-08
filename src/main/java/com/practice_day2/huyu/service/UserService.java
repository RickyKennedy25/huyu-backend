package com.practice_day2.huyu.service;

import com.practice_day2.huyu.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User createUser(User user);
    public User updateUser(User user) throws Exception;
    public User readUser(String id);
    public ResponseEntity deleteUser(String id);
}
