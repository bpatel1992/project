package com.rahul.project.gateway.chat.dto;

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
