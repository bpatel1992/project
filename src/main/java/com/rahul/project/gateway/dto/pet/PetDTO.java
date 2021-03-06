package com.rahul.project.gateway.dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.dto.GenderDTO;
import com.rahul.project.gateway.dto.PetBreedDTO;
import com.rahul.project.gateway.dto.WeightUnitDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author rahul malhotra
 * @Date 2019-04-30
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {

    private Long id;
    private String userName;
    private String name;
    private String imageURL;
    private String randomKey;
    private String relation;
    private WeightUnitDTO weightUnit;
    private Double weightValue;
    private GenderDTO gender;
    private PetBreedDTO petBreed;
    private String dob;
    private Integer yearOld;
    private String creationDate;
    private String modificationDate;
}
