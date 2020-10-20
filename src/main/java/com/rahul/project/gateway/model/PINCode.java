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
@Table(name = "pin_code_m")
public class PINCode implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;
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
    @JoinColumn(name = "city_id")
    private City city;

    public PINCode(Long id) {
        this.id = id;
    }
}
