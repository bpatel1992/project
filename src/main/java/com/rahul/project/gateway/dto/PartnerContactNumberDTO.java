package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerContactNumberDTO {

    private Long id;
    private String mobile;
    private String firstName;
    private String lastName;
    private MasterDataDTO title;
    private MasterDataDTO country;
}

