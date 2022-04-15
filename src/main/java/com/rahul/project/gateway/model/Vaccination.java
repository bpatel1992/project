package com.rahul.project.gateway.model;

import lombok.Data;

import java.util.Date;

@Data
public class Vaccination {

    private String vaccinationName;
    private String petName;
    private Date dateOfVaccination;
    private Date dueDate;
    private String vaccineLabel;
}
