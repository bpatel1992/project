
package com.rahul.project.gateway.dto.enablex.create.room.response;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Sip {

    private Boolean enabled;
    private long meetingId;
    private long pin;

}
