package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.utility.TextSource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private String randomKey;
    private String responseCode;
    private String responseMessage;
    private List<String> certificateURLs;
    private List<String> galleryURLs;
    private Boolean activated;
    private String image;
    private String fileName;
    private Long id;
    private Boolean allowed;

    public ResponseDTO() {
        this.responseCode = "0000";
        this.responseMessage = TextSource.getText("api.success");
    }

    public ResponseDTO(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ResponseDTO(String responseMessage) {
        responseMessage = TextSource.getText(responseMessage);
        if (responseMessage.contains(":")) {
            this.responseCode = responseMessage.substring(0, responseMessage.lastIndexOf(":"));
            this.responseMessage = responseMessage.substring(responseMessage.lastIndexOf(":") + 1);
        } else {
            this.responseCode = "XXXX";
            this.responseMessage = responseMessage;
        }
    }
}
