package com.practice_day2.huyu.controller;

import com.practice_day2.huyu.model.User;
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

    @GetMapping("/hello-Dosen")
    public String helloDosen() {
        return "Hello Dosen";
    }

    @GetMapping("/user/{id}")
    @Cacheable(key = "#id", value="user")
    public User findUser(@PathVariable("id") String id) {
        return userService.readUser(id);
    }

    @PostMapping("/user")
    @CacheEvict(value="user", key="#user.id")
    public User saveUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user")
    @CacheEvict(value="user", key="#user.id")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        return deleteUser(id);
    }

}
