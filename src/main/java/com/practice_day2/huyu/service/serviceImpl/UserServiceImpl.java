package com.practice_day2.huyu.service.serviceImpl;

import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.repository.UserRepository;
import com.practice_day2.huyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User readUser(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public ResponseEntity deleteUser(String id) {
        User delete = userRepository.findOne(id);
        return ResponseEntity.ok().build();
    }
}
