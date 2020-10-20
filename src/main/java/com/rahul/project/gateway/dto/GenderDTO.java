package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author rahul malhotra
 * @Date 2020-04-30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenderDTO {

    private Long id;
    private String name;
    private String image;
}
