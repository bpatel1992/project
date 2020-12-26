package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="chatMessage")
public class ChatMessage  extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private Long senderId;

  @NotNull
  @Basic
  @Column(name = "senderName")
  private String senderName;

  @NotNull
  private Long receiverId;

  @NotNull
  @Basic
  @Column(name = "receiverName")
  private String receiverName;

  @NotNull
  private Date timeSent;

  @NotNull
  private String contents;

  public ChatMessage() {}

  public ChatMessage(Long senderId,String senderName,String receiverName, Long receiverId, String contents) {
    this.senderId = senderId;
    this.senderName = senderName;
    this.receiverId = receiverId;
    this.receiverName = receiverName;
    this.contents = contents;
    this.timeSent = new Date();
  }
  

}