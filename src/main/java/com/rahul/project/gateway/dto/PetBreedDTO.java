package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * @Date 2019-04-30
 */
public class PetBreedDTO {

    private Long id;
    private String name;
    private String petType;
    private PetFamilyDTO petFamily;
    private PetBreedLifespanDTO petBreedLifespan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public PetFamilyDTO getPetFamily() {
        return petFamily;
    }

    public void setPetFamily(PetFamilyDTO petFamily) {
        this.petFamily = petFamily;
    }

    public PetBreedLifespanDTO getPetBreedLifespan() {
        return petBreedLifespan;
    }

    public void setPetBreedLifespan(PetBreedLifespanDTO petBreedLifespan) {
        this.petBreedLifespan = petBreedLifespan;
    }
}
