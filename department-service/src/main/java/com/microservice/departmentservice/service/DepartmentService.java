package com.microservice.departmentservice.service;

import com.microservice.departmentservice.model.Department;
import com.microservice.departmentservice.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public void addDepartment(Department req) {

        Department department=new Department();
        department.setName(req.getName());
        departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) throws Exception {
        return departmentRepository.findById(id).orElseThrow(()-> new Exception("No Department Found"));
    }
}
