package com.microservice.identityservice.repository;

import com.microservice.identityservice.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users,String> {
    Users findByUserName(String username);
}
