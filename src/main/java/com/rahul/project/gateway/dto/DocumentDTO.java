
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentDTO {

    private Integer displayOrder;
    private Long docTypeId;
    private String title;
    private String image;
    private Long id;
    private String documentNumber;
    private String videoCode;
}
