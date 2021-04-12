package com.rahul.project.gateway.dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.dto.GenderDTO;
import com.rahul.project.gateway.dto.PetBreedDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author rahul malhotra
 * @Date 2020-Apr-12
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetListDTO {

    private Long id;
    private String userName;
    private String name;
    private String imageURL;
    private String relation;
    private GenderDTO gender;
    private PetBreedDTO petBreed;
    private Integer yearOld;
}
