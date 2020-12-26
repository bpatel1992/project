package com.rahul.project.gateway.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatChannelInitializationDTO {
  private Long senderId;

  private Long receiverId;

  private String senderName;

  private String receiverName;

  public ChatChannelInitializationDTO() {}
}
