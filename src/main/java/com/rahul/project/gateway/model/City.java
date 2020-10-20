package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "city_m")
public class City implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Basic
    @Column(name = "status")
    private boolean status;
    @Basic
    @Column(name = "code")
    private Integer code;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    public City(Long id) {
        this.id = id;
    }

}
