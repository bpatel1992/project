package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "question")
@Getter
@Setter
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "question")
    private String question;

    @Basic
    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;

    @ManyToMany()
    @JoinTable(name = "question_datapoint", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "datapoint_id"))
    private Set<DataPoint> dataPoints;
}

