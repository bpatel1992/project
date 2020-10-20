package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class PossibleCauseId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "pet_sign_id")
    private PetSign petSign;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;
}
