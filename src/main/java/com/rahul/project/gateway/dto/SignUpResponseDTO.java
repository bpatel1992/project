package com.rahul.project.gateway.dto;

import lombok.Data;

/**
 * @author rahul malhotra
 * date 2019-11-09
 */
@Data
public class SignUpResponseDTO {

    private String randomKey;

    private String responseMessage;
}
