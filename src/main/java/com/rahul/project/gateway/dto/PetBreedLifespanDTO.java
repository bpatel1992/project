package com.rahul.project.gateway.dto;

/**
 * @author rahul malhotra
 * @Date 2019-04-30
 */
public class PetBreedLifespanDTO {

    private Long id;
    private Double lowerLimit;
    private Double upperLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }
}
