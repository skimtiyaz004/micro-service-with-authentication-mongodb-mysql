package com.microservice.employeeserver.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface DepartmentClient {
    @GetExchange("/department/get/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id);
}
