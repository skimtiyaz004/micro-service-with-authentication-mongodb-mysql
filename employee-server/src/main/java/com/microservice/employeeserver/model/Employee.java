package com.microservice.employeeserver.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Employee {
    @Id
    private String id;
    private String name;
    private Long departmentId;
}
