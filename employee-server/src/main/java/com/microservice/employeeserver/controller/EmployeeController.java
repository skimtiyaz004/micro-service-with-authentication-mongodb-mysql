package com.microservice.employeeserver.controller;

import com.microservice.employeeserver.model.Employee;
import com.microservice.employeeserver.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee req){
        employeeService.addEmployee(req);
        return new ResponseEntity<>("Data added", HttpStatus.OK);
    }
}


