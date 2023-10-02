package com.microservice.identityservice.service;

import com.microservice.identityservice.model.Users;
import com.microservice.identityservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersSerivce {
    @Autowired
    private UsersRepository userRepository;

    public Users getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public void addUser(Users users) {
        userRepository.save(users);
    }
}
