package com.microservice.identityservice.repository;

import com.microservice.identityservice.model.ERole;
import com.microservice.identityservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByRoleName(ERole role);
}
