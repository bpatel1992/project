package com.rahul.project.gateway.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

  private String type;

  private String message;

  private Long senderId;

  private Long receiverId;

  private String senderName;

  private String receiverName;

  private String date;

  private String time;

  public ChatMessageDTO(){}

  public ChatMessageDTO(Long senderId,String senderName,String receiverName, Long receiverId,String message,String date,String time) {
    this.message = message;
    this.senderId = senderId;
    this.senderName = senderName;
    this.receiverName = receiverName;
    this.receiverId = receiverId;
    this.date = date;
    this.time = time;
  }

}
