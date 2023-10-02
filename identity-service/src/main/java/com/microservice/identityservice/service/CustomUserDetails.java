package com.microservice.identityservice.service;

import com.microservice.identityservice.model.ERole;
import com.microservice.identityservice.model.Users;
import com.microservice.identityservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=userRepository.findByUserName(username);
        if(users==null){
            System.out.println("No User Found");
        }
        Collection<SimpleGrantedAuthority> authorities=users.getRole().
                stream().map(role->new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        UserDetails user= null;
        try {
            user = User.withUsername(users.getUserName())
                    .password(users.getPassword())
                    .authorities(authorities)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // return new User(users.getUserName(),users.getPassword(),authorities);
        return user;
    }
}
