package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.utility.TextSource;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseHandlerDTO<T> {

    private String responseCode;
    private String responseMessage;
    private T t;

    public ResponseHandlerDTO() {
        this.responseCode = "0000";
        this.responseMessage = TextSource.getText("api.success");
    }

    public ResponseHandlerDTO(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ResponseHandlerDTO(String responseMessage) {
        this.responseCode = "0000";
        this.responseMessage = responseMessage;
    }
}
