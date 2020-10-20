package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "possible_cause_m", uniqueConstraints = @UniqueConstraint(columnNames = {"assessment_option_id", "assessment_id", "symptom_node_id",}))
public class PossibleCause implements Serializable {

    @Id
    @SequenceGenerator(name = "possible_cause_gen", allocationSize = 1, sequenceName = "possible_cause_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "possible_cause_gen")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"assessment", "petType", "symptomNodeAssessment"})
    @JoinColumn(name = "symptom_node_id")
    private SymptomNodeAssessment symptomNodeAssessment;

    @ManyToOne
    @JoinColumn(name = "possible_cause_priority_id")
    private PossibleCausePriority possibleCausePriority;

    @ManyToOne
    @JoinColumn(name = "assessment_option_id")
    private AssessmentOption assessmentOption;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    @JsonIgnoreProperties("assessmentOptions")
    private Assessment assessment;

    @Column(name = "possible_cause_name")
    private String cause;

    // preventive care at home
    @ManyToMany()
    @JoinTable(name = "prevention_pc_mp", joinColumns = {@JoinColumn(name = "possible_cause_id")},
            inverseJoinColumns = @JoinColumn(name = "prevention_id"))
    private Set<Prevention> preventions;

    // potential problem
    @ManyToMany()
    @JoinTable(name = "pet_condition_pc_mp", joinColumns = @JoinColumn(name = "possible_cause_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_condition_id"))
    private Set<PetCondition> petConditions;

    // what to do next
    @ManyToMany()
    @JoinTable(name = "wtdn_pc_mp", joinColumns = @JoinColumn(name = "possible_cause_id"),
            inverseJoinColumns = @JoinColumn(name = "wtdn_id"))
    private Set<WTDN> wtdns;

    // preventive care at home
    /*@ManyToMany()
    @JoinTable(name = "remedy_pc_mp", joinColumns = @JoinColumn(name = "possible_cause_id"),
            inverseJoinColumns = @JoinColumn(name = "remedy_id"))
    private Set<Remedy> remedies;*/

}
