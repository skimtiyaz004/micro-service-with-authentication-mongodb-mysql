package com.microservice.departmentservice.controller;

import com.microservice.departmentservice.model.Department;
import com.microservice.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Department req){
        departmentService.addDepartment(req);
        return new ResponseEntity<>("Data added", HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id) throws Exception {
         Department department=departmentService.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
