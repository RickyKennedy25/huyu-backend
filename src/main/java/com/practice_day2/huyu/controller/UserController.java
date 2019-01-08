package com.practice_day2.huyu.controller;

import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public User findUser(@PathVariable("id") String id) {
        return userService.readUser(id);
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        return deleteUser(id);
    }

}
