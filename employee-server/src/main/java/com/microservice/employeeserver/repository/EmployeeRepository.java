package com.microservice.employeeserver.repository;

import com.microservice.employeeserver.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
}
