package com.rahul.project.gateway.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class FunctionalityDTO {
    private Long id;

    private String name;

    private String icon;

    private String route;

    private Integer displayOrder;

    private String description;

    private boolean status;

}
