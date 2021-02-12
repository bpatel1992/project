package com.rahul.project.gateway.video.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name="videoChannel")
public class VideoChannel {

  @Id
  @NotNull
  private String sessionId;

  @NotNull
  private Long partnerId;

  @NotNull
  private Long userId;

  public VideoChannel() {}

  public VideoChannel(String sessionId,Long partnerId, Long userId) {
    this.sessionId = sessionId;
    this.partnerId = partnerId;
    this.userId = userId;
  }


}