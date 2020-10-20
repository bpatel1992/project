package com.rahul.project.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class RoleDTO {

    private Long id;
    private List<RoleFunctionality> roleFunctionality;
    private String roleName;
    private String roleDesc;
    private boolean status;

}
