package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "possible_cause_feedback_m")
public class PossibleCauseFeedback implements Serializable {

    @Id
    @SequenceGenerator(name = "possible_cause_feedback_gen", allocationSize = 1,
            sequenceName = "possible_cause_feedback_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "possible_cause_feedback_gen")
    private Long id;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JsonIgnoreProperties({"wtdns", "petConditions", "preventions", "assessment", "assessmentOption", "possibleCausePriority"
            , "symptomNodeAssessment"})
    @JoinColumn(name = "possible_cause_id")
    private PossibleCause possibleCause;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
