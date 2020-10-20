package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class AssessOptionSignId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "assessment_id", referencedColumnName = "id")
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "assessment_option_id", referencedColumnName = "id")
    private AssessmentOption assessmentOption;

    @ManyToOne
    @JoinColumn(name = "pet_sign_id", referencedColumnName = "id")
    private PetSign petSign;
}
