package com.rahul.project.gateway.chat.dto;

import lombok.Data;

@Data
public class NotificationDTO {
  private String type;

  private String contents;

  private Long fromUserId;

  public NotificationDTO() {}

  public NotificationDTO(String type, String contents, Long fromUserId) {
    this.type = type;
    this.contents = contents;
    this.fromUserId = fromUserId;
  }
}