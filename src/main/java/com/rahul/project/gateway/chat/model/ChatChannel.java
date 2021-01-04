package com.rahul.project.gateway.chat.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@Entity
@Table(name="chatChannel")
public class ChatChannel {

  @Id
  @NotNull
  private String channelId;

  @NotNull
  private Long senderId;

  @NotNull
  private Long receiverId;

  public ChatChannel() {}

  public ChatChannel(Long senderId, Long receiverId) {
    this.channelId = UUID.randomUUID().toString();
    this.senderId = senderId;
    this.receiverId = receiverId;
  }


}