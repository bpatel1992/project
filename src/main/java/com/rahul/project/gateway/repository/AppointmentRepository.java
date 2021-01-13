package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Appointment Repository to handle any Appointment related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AppointmentRepository")
public interface AppointmentRepository extends BaseRepository<Appointment, Long> {

    @Query(value = "SELECT a.id FROM Appointment a " +
            "  WHERE a.appointmentDate= ?4 and a.attendant.id = ?1 and a.clinic.id = ?2 and " +
            " a.appointmentFromTime = ?3 and a.appointmentStatus in (?5)")
    List<Long> appointmentExist(Long user, Long partnerAddress, Date time, Date date,
                                List<Appointment.AppointmentStatus> appointmentStatuses);

    @Query(value = "SELECT a FROM Appointment a " +
            "  WHERE a.attendant.id = ?1 and a.appointmentDate= ?2 order by a.appointmentFromTime asc")
    List<Appointment> appointmentListByDate(Long partnerAddress, Date time);

    @Query(value = "SELECT a FROM Appointment a " +
            "  WHERE a.appointmentType.appointmentType = ?1 and a.isVideoTokenGenerated=?2  and a.statusType.id in (?3) order by a.appointmentFromTime asc")
    List<Appointment> findAppointmentsByAppointmentTypeAndStatus(String appointmentType,boolean isVideoTokenGenerated, List<Long> appointmentStatuses);

    @Query(value = "SELECT a FROM Appointment a " +
            "  WHERE a.appointmentType.appointmentType = ?1 and a.id=?2 and a.isVideoTokenGenerated=?3  and a.statusType.id in (?4) order by a.appointmentFromTime asc")
    Appointment findByOnlineConsultationAppointmentId(String appointmentType,Long appointmentId,boolean isVideoTokenGenerated,List<Long> appointmentStatuses);
}
