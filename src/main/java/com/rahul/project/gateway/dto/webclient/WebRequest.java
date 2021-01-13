package com.rahul.project.gateway.dto.webclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebRequest {

    private String apiUrl;
    private String method;
    private Map<String,String> headers;
    private Map<String,String> formPayload;
    private Map<String,String> queryParams;
    @Builder.Default
    private String contentType = "application/json";
    private String payload;
    private Map<String,Object> jsonPaylad;
    @Builder.Default
    private long timeOutMs=30000;
    private boolean buildURI;

}