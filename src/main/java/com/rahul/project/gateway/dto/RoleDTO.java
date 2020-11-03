package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {
    private Long id;
    //private RoleFunctionality roleFunctionalityId;
    private List<Functionality> functionalities;
    private String roleName;
    private boolean status;

}
