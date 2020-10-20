package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author rahul malhotra
 * date 2020-09-14
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reminder")
public class Reminder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("pets")
    private User user;
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    @JsonIgnoreProperties("users")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    private ServiceType serviceType;
    @ManyToOne
    @JoinColumn(name = "reminder_type_id", referencedColumnName = "id")
    private ReminderType reminderType;
    @ManyToOne
    @JoinColumn(name = "status_type_id", referencedColumnName = "id")
    private StatusType statusType;
    @Basic
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic
    @Column(name = "notification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date notificationDate;
    @Basic
    @Column(name = "schedule_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date scheduleDate;

    public Reminder(Long id) {
        this.id = id;
    }

}
