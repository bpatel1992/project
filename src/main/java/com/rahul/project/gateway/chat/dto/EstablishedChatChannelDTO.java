package com.rahul.project.gateway.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstablishedChatChannelDTO {
    private String channelUuid;

    private String senderName;

    private String receiverName;

    private Long senderId;

    private Long receiverId;

    public EstablishedChatChannelDTO() {
    }

    public EstablishedChatChannelDTO(String channelUuid, Long senderId, String senderName, Long receiverId, String receiverName) {
        this.channelUuid = channelUuid;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }
}