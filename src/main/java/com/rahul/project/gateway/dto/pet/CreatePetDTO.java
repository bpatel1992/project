package com.rahul.project.gateway.dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * @Date 2020-10-13
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePetDTO {

    private Long id;
    private String userName;
    @NotNull(message = "Validator.notNullMsg")
    private String name;
    private String imageURL;
    private String randomKey;
    private Long weightUnitId;
    private Double weightValue;
    private Long genderId;
    private Long petTypeId;
    private Long petBreedId;
    private String birthDay;

    private Integer yearOld;
    @NotNull(message = "Validator.notNullMsg")
    private Long customerId;
    private Long partnerId;
}
