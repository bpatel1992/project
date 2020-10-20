package com.rahul.project.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class RoleFunctionality {

    private List<RoleFeature> features;
    private Functionality functionality;
    private Long id;
    private List<Privilege> privileges;

}
