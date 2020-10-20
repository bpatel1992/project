package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "assessment_option_pet_sign_m")
public class AssessOptionSign implements Serializable {

    @EmbeddedId
    private AssessOptionSignId id;
}
