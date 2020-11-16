package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {
    private Long id;
    private Set<RoleFunctionality> roleFunctionality;
    private String roleName;
    private boolean status;

}
