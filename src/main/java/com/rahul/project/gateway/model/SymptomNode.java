package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "symptom_node_m")
public class SymptomNode implements Serializable {

    @Id
    @SequenceGenerator(name = "symptom_node_gen", allocationSize = 1, sequenceName = "symptom_node_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "symptom_node_gen")
    private Long id;

    @Column(name = "symptom_node_name", length = 100)
    private String symptomNode;

    @ManyToMany(mappedBy = "symptomNode")
    @JsonIgnore
//    @JsonIgnoreProperties({"assessment", "symptomNode", "petType"})
    private Set<SymptomNodeAssessment> symptomNodeAssessments;

}
