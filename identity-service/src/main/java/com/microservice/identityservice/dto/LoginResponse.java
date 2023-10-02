package com.microservice.identityservice.dto;

import com.microservice.identityservice.model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
public class LoginResponse {
    private String id;
    private String name;
    private String email;
    private String userName;
    private Set<Role> role = new HashSet<>();
    private String token;
}
