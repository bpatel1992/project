package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User Country Entity
 *
 * @author Rahul Malhotra
 */
@Setter
@Getter
@Entity
@Table(name = "currency_m")
public class Currency implements Serializable {

    private static final long serialVersionUID = -87874056435559040L;
    @Id
    @Column(name = "currency")
    private String currency;
    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean enabled;

    public Currency() {
    }

    public Currency(String currency) {
        this.currency = currency;
    }

}
