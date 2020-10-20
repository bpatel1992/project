package com.rahul.project.gateway.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
public class PetLicenceMpPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "licence_id", referencedColumnName = "id")
    private PetLicenceM petLicenceM;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public PetLicenceM getPetLicenceM() {
        return petLicenceM;
    }

    public void setPetLicenceM(PetLicenceM petLicenceM) {
        this.petLicenceM = petLicenceM;
    }
}
