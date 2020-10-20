package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO {

    private long id;
    private String name;
    private String image;
    private Integer code;
    private Integer fromLength;
    private Integer toLength;
    private String ctDesc;
}
