
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class Pet {
    private Long id;
    private String image;
    private String name;

    public Pet(Long id) {
        this.id = id;
    }

}
