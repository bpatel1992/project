
package com.rahul.project.gateway.dto.enablex.error;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class ErrorResponse {

    private String desc;
    private String error;
    private long result;

}
