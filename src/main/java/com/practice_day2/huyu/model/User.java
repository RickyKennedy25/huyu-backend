package com.practice_day2.huyu.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "user")
public class User implements Serializable {

    private String id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    private String role;
}
