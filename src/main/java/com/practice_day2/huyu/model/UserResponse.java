package com.practice_day2.huyu.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponse implements Serializable {

    private String id;

    private String name;

    private String username;

    private String role;

}
