package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterDataDTO {

    private Long id;
    private String language;
    private String name;
    private String image;
    private String data;
    private String description;
    private Integer code;
    private String addressType;
    private Boolean status;
}

