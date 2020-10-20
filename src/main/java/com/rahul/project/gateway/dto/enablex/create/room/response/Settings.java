
package com.rahul.project.gateway.dto.enablex.create.room.response;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Settings {

    private Boolean adhoc;
    private Boolean autoRecording;
    private String billingCode;
    private Boolean canvas;
    private String description;
    private long duration;
    private Boolean maxActiveTalkers;
    private String mode;
    private String moderators;
    private String participants;
    private String quality;
    private Boolean scheduled;
    private Boolean screenShare;

}
