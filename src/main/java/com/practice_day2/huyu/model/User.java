package com.practice_day2.huyu.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;

@Data
@Document(collection = "user")
public class User implements Serializable  {

    private String id = new ObjectId().toString();

    private String name;

    private String username;

    private String password;

    private String role;
}
