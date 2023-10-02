package com.microservice.identityservice.service;

import com.microservice.identityservice.model.ERole;
import com.microservice.identityservice.model.Role;
import com.microservice.identityservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role getRoleByName(ERole role) throws Exception {
        Role roles=roleRepository.findByRoleName(role).orElseThrow(()->new Exception("No Role Found"));
        return roles;
    }
}
