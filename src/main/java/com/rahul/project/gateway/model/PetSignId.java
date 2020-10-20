package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class PetSignId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "assessment_option_id")
    private AssessmentOption assessmentOption;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;
}
