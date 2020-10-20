
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenInfoResponseDTO {

    private String accessType;
    private String audience;
    private String email;
    private String id;
    private long expiresIn;
    private String issuedTo;
    private String scope;
    private String userId;
    private Boolean verifiedEmail;

}
