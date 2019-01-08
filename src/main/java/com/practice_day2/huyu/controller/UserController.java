package com.practice_day2.huyu.controller;

import com.practice_day2.huyu.mapper.UserMapper;
import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.model.UserResponse;
import com.practice_day2.huyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/hello-mahasiswa")
    public String helloMahasiswa() {
        return "Hello Mahasiswa";
    }

    @GetMapping("/hello-dosen")
    public String helloDosen() {
        return "Hello Dosen";
    }

    @GetMapping("/user/{id}")
    @Cacheable(key = "#id", value="user")
    public UserResponse findUser(@PathVariable("id") String id) {
        User _user = userService.readUser(id);

        return userMapper.userToUserRespone(_user);
    }

    @PostMapping("/user")
    @CacheEvict(value="user", key="#user.id")
    public UserResponse saveUser(@RequestBody User user) {
        User _user = userService.createUser(user);
        return userMapper.userToUserRespone(_user);
    }

    @PutMapping("/user")
    @CacheEvict(value="user", key="#user.id")
    public UserResponse updateUser(@RequestBody User user) {
        User _user = userService.updateUser(user);
        return userMapper.userToUserRespone(_user);
    }

    @DeleteMapping("/user/{id}")
    @CacheEvict(key = "#id", value="user")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(id);
    }

}
