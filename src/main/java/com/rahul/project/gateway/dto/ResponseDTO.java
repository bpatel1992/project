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
        this.responseCode = "0000";
        this.responseMessage = responseMessage;
    }
}
