package com.microservice.identityservice.dto;

import com.microservice.identityservice.model.ERole;
import lombok.Data;

@Data
public class RoleDTO {
    private ERole roleName;
}
