package com.rahul.project.gateway.dto;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * @Date 2019-05-06
 */
public class UserDeviceDTO {

    @NotNull(message = "Validator.notNullMsg")
    private String deviceToken;

    @NotNull(message = "Validator.notNullMsg")
    private String platform;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
