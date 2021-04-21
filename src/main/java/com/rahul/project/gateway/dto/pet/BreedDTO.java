package com.rahul.project.gateway.dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.dto.BaseDTO;
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
public class BreedDTO extends BaseDTO {

    //    private Long id;
//    private String name;
    private BaseDTO petType;
}
