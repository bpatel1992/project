package com.rahul.project.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class RoleFeature {

    private Feature feature;
    private Long id;
    private List<Privilege> privileges;

}
