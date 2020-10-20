package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "pet_licence_mp", schema = "gateway", catalog = "")
@IdClass(PetLicenceMpPK.class)
public class PetLicenceMp implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @Id
    @ManyToOne
    @JoinColumn(name = "licence_id", referencedColumnName = "id")
    private PetLicenceM petLicenceM;
}
