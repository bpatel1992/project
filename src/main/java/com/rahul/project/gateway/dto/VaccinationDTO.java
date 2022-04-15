package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccinationDTO {

    private String vaccinationName;
    private String petName;
    private Date dateOfVaccination;
    private Date dueDate;
    private String vaccineLabel;
}
