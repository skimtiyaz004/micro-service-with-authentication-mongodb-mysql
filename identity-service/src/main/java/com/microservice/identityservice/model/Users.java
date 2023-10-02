package com.microservice.identityservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
public class Users {
    @Id
    private String id;
    private String name;
    private String email;
    private Set<Role> role = new HashSet<>();
    private String userName;
    private String password;
}
