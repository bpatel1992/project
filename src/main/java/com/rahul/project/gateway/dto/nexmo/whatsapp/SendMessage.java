
package com.rahul.project.gateway.dto.nexmo.whatsapp;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class SendMessage {

    private From from;
    private Message message;
    private To to;

}
