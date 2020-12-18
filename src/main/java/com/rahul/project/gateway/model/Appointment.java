package com.rahul.project.gateway.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rahul.project.gateway.serialize.AppointmentStatusSerializer;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "appointment_m")
public class Appointment extends BaseEntity {

    @Id
    @SequenceGenerator(name = "appointment_m_gen", allocationSize = 1, sequenceName = "appointment_m_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_m_gen")
    private Long id;

    @JoinColumn(name = "appointment_status")
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private PartnerAddress clinic;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties({"userPetRelationMPS", "authorities"})
    private User customer;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnoreProperties({"users", "authorities"})
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @ManyToOne
    @JoinColumn(name = "attendant_id")
    @JsonIgnoreProperties({"userPetRelationMPS", "authorities"})
    private User attendant;
    @ManyToOne
    @JoinColumn(name = "appointment_type_id")
    private AppointmentType appointmentType;
    @ManyToOne
    @JoinColumn(name = "appointment_reason_id")
    private AppointmentReason appointmentReason;
    @Column(name = "appointment_reason_others")
    private String appointmentReasonOthers;
    @Temporal(TemporalType.DATE)
    private Date appointmentDate;
    @Temporal(TemporalType.TIME)
    private Date appointmentFromTime;
    @Temporal(TemporalType.TIME)
    private Date appointmentToTime;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusType statusType;
    @ManyToOne
    @JoinColumn(name = "appointment_repeat_id")
    private AppointmentRepeat appointmentRepeat;

    @Column(name = "occurrence")
    private Integer occurrence;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services services;
    @Column(name = "is_payment_done")
    private boolean isPaymentDone;
    @Basic
    @CreatedDate
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic
    @LastModifiedDate
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modificationDate;

    public enum AppointmentStatusType {
        SCHEDULED, ACTIVE, CANCELLED, MISSED, COMPLETED,PENDING,ARRIVED
    }

    // TODO confirm that "WALK-IN" should be considered active and "RESCHEDULED"
    // should be scheduled
    @JsonSerialize(using = AppointmentStatusSerializer.class)
    public enum AppointmentStatus {

        SCHEDULED("Scheduled", AppointmentStatusType.SCHEDULED),
        RESCHEDULED("Rescheduled", AppointmentStatusType.SCHEDULED),
        WALKIN("Walk-In", AppointmentStatusType.ACTIVE),
        WAITING("Waiting", AppointmentStatusType.ACTIVE),
        INCONSULTATION("In-Consultation", AppointmentStatusType.ACTIVE),
        CANCELLED("Cancelled", AppointmentStatusType.CANCELLED),
        CANCELLED_AND_NEEDS_RESCHEDULE("Cancelled and Needs Reschedule", AppointmentStatusType.CANCELLED),
        MISSED("Missed", AppointmentStatusType.MISSED),
        COMPLETED("Completed", AppointmentStatusType.COMPLETED),
        PENDING("PENDING", AppointmentStatusType.PENDING),
        ARRIVED("ARRIVED", AppointmentStatusType.ARRIVED);

        private final String name;

        private final AppointmentStatusType type;

        AppointmentStatus(final String name,
                          final AppointmentStatusType type) {
            this.name = name;
            this.type = type;
        }

        public static List<AppointmentStatus> getAppointmentsStatusByTypes(
                List<AppointmentStatusType> appointmentStatusTypes) {
            List<AppointmentStatus> appointmentStatuses = new ArrayList<AppointmentStatus>();

            for (AppointmentStatus appointmentStatus : AppointmentStatus
                    .values()) {
                if (appointmentStatusTypes
                        .contains(appointmentStatus.getType())) {
                    appointmentStatuses.add(appointmentStatus);
                }
            }

            return appointmentStatuses;
        }

        public static List<AppointmentStatus> getAppointmentsStatusByType(
                AppointmentStatusType appointmentStatusType) {
            return getAppointmentsStatusByTypes(Collections
                    .singletonList(appointmentStatusType));
        }

        public static List<AppointmentStatus> getNotCancelledAppointmentStatuses() {
            return getAppointmentsStatusByTypes(Arrays.asList(
                    AppointmentStatusType.ACTIVE,
                    AppointmentStatusType.COMPLETED,
                    AppointmentStatusType.MISSED,
                    AppointmentStatusType.SCHEDULED));
        }

        public static List<AppointmentStatus> getBookedAppointmentStatuses() {
            return getAppointmentsStatusByTypes(Arrays.asList(
                    AppointmentStatusType.ACTIVE,
//                    AppointmentStatusType.COMPLETED,
//                    AppointmentStatusType.MISSED,
                    AppointmentStatusType.SCHEDULED));
        }


        public String getName() {
            return this.name;
        }

        public AppointmentStatusType getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
