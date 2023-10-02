package com.microservice.identityservice.controller;

import com.microservice.identityservice.dto.LoginDTO;
import com.microservice.identityservice.dto.LoginResponse;
import com.microservice.identityservice.dto.RoleDTO;
import com.microservice.identityservice.dto.UserDTO;
import com.microservice.identityservice.model.ERole;
import com.microservice.identityservice.model.Role;
import com.microservice.identityservice.model.Users;
import com.microservice.identityservice.service.CustomUserDetails;
import com.microservice.identityservice.service.RoleService;
import com.microservice.identityservice.service.UsersSerivce;
import com.microservice.identityservice.utils.JwtUtil;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UsersSerivce usersSerivce;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetails customUserDetails;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody RoleDTO req) {
        Role role = new Role();
        role.setRoleName(req.getRoleName());
        roleService.addRole(role);
        return new ResponseEntity<>("Role added", HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@NonNull @RequestBody UserDTO req) throws Exception {
        Users us = usersSerivce.getUserByUserName(req.getUserName().toLowerCase());
        if (us != null) {
            System.out.println("User exits");

        }
        Users users = new Users();
        users.setName(req.getName());
        users.setEmail(req.getEmail());
        int userNameString = req.getUserName().length();
        users.setUserName(req.getUserName().toLowerCase());
        Set<Role> roles = new HashSet<>();
        Role myRole = roleService.getRoleByName(ERole.ROLE_SUPER_ADMIN);
        roles.add(myRole);
        users.setRole(roles);
        users.setPassword(passwordEncoder.encode(req.getPassword()));
        usersSerivce.addUser(users);
        return new ResponseEntity<>("User added", HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@NonNull @RequestBody LoginDTO req) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername().toLowerCase(), req.getPassword()));

        } catch (Exception exception) {

            throw new Exception("Invalid Username or Password");
        }
        Users users = usersSerivce.getUserByUserName(req.getUsername().toLowerCase());

        final UserDetails userDetails = customUserDetails.loadUserByUsername(req.getUsername().toLowerCase());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String newGeneratedToken = jwtUtil.generateToken((org.springframework.security.core.userdetails.User) userDetails);
        Users user = usersSerivce.getUserByUserName(userDetails.getUsername().toLowerCase());
        LoginResponse response = new LoginResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRole(user.getRole());
        response.setUserName(user.getUserName());
        response.setToken(newGeneratedToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtUtil.verifyJwtToken(token);
        return "Token is valid";
    }
}
