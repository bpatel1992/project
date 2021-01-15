package com.rahul.project.gateway.video.model;

import com.rahul.project.gateway.model.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name="VideoAppointmentMapping")
public class VideoAppointmentMapping extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String sessionId;

  @NotNull
  @Basic
  @Column(name = "appointmentId")
  private Long appointmentId;

  @NotNull
  @Temporal(TemporalType.TIME)
  private Date appointmentFromTime;

  @NotNull
  @Length(max = 600)
  private String token;

  @Basic
  @Column(name = "archiveId")
  private String archiveId;

  @NotNull
  @Basic
  @Column(name = "expiry")
  private Long expiryInMin;

  @Basic
  @Column(name = "userKey")
  private String userKey;

  @Basic
  @Column(name = "partnerKey")
  private String partnerKey;

  public VideoAppointmentMapping() {}

  public VideoAppointmentMapping(@NotNull String sessionId,
                                 @NotNull Long appointmentId,@NotNull Date appointmentFromTime,
                                 @NotNull String token, String archiveId,
                                 @NotNull Long expiryInMin,@NotNull String userKey,
                                 @NotNull String partnerKey) {
    this.sessionId = sessionId;
    this.appointmentId = appointmentId;
    this.appointmentFromTime = appointmentFromTime;
    this.token = token;
    this.archiveId = archiveId;
    this.expiryInMin = expiryInMin;
    this.userKey = userKey;
    this.partnerKey = partnerKey;
  }
}