
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class Client {
    private Long id;
    private String image;
    private String name;

    public Client(Long id) {
        this.id = id;
    }

}
