package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "pet_sign_m", uniqueConstraints = @UniqueConstraint(columnNames = {"assessment_option_id", "assessment_id", "symptom_node_id"}))
public class PetSign implements Serializable {

    @Id
    @SequenceGenerator(name = "pet_sign_gen", allocationSize = 1, sequenceName = "pet_sign_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_sign_gen")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"assessment", "petType", "symptomNode"})
    @JoinColumn(name = "symptom_node_id")
    private SymptomNodeAssessment symptomNodeAssessment;

    @ManyToOne
    @JoinColumn(name = "assessment_option_id")
    private AssessmentOption assessmentOption;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    @JsonIgnoreProperties("assessmentOptions")
    private Assessment assessment;

    @Column(name = "pet_sign_name", length = 100)
    private String sign;

    @ManyToOne
    @JoinColumn(name = "next_assessment_id")
    private Assessment nextAssessment;
}
