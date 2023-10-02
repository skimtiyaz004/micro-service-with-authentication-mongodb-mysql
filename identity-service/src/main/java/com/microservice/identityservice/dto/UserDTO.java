package com.microservice.identityservice.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String userName;
}
