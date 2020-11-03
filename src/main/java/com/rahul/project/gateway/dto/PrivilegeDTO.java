package com.rahul.project.gateway.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class PrivilegeDTO {

    private Long id;
    private String privilege;
    private String privilegeDesc;
    private boolean status;



}
