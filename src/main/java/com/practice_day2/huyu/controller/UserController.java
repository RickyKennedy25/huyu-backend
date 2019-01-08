package com.practice_day2.huyu.controller;

import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.model.UserResponse;
import com.practice_day2.huyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

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
        return userToUserRespone(_user);
    }

    @PostMapping("/user")
    @CacheEvict(value="user", key="#user.id")
    public UserResponse saveUser(@RequestBody User user) {
        User _user = userService.createUser(user);
        return userToUserRespone(_user);
    }

    @PutMapping("/user")
    @CacheEvict(value="user", key="#user.id")
    public UserResponse updateUser(@RequestBody User user) {
        User _user = userService.updateUser(user);
        return userToUserRespone(_user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        return deleteUser(id);
    }

    public UserResponse userToUserRespone(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
    }
}
