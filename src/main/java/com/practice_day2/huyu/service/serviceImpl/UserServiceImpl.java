package com.practice_day2.huyu.service.serviceImpl;

import com.practice_day2.huyu.model.NotFoundException;
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
        if (userRepository.findOne(user.getId()).equals(null)) {
            throw new NotFoundException("user doesn't exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User readUser(String id) {
        User user = userRepository.findOne(id);
        if (user.equals(null)) {
            throw new NotFoundException("user not found");
        }
        return user;
    }

    @Override
    public ResponseEntity deleteUser(String id) {
        User user = userRepository.findOne(id);

        if (user.equals(null)) {
            throw new NotFoundException("the user you wants to delete is not found");
        }
        return ResponseEntity.ok().build();
    }


}
