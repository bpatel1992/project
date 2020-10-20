package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "symptom_node_assessment_mp", uniqueConstraints = @UniqueConstraint(columnNames = {"pet_type_id", "assessment_id", "symptom_node_id"}))
public class SymptomNodeAssessment implements Serializable {

    @Id
    @SequenceGenerator(name = "symptom_node_assessment_mp_gen", allocationSize = 1, sequenceName = "symptom_node_assessment_mp_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "symptom_node_assessment_mp_gen")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"status", "localizations"})
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    @JsonIgnoreProperties({"localizations"})
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "symptom_node_id")
    @JsonIgnoreProperties("symptomNodeAssessments")
    private SymptomNode symptomNode;

}
