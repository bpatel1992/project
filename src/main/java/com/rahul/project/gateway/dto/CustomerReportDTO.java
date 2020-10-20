
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerReportDTO {

    private List<Client> clients;
    private String email;
    private String lastVisit;
    private List<Pet> pets;
    private String mobile;
    private String petType;
    private String petBreed;
    private String gender;
    private Integer yearOld;
    private String randomKey;
}
