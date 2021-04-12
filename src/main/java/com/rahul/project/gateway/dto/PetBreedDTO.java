package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PetBreedDTO {

    private Long id;
    private String name;
    private MasterDataDTO petType;
    private PetFamilyDTO petFamily;
    private PetBreedLifespanDTO petBreedLifespan;
}
