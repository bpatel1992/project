package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Appointment;
import org.springframework.stereotype.Repository;

/**
 * Appointment Repository to handle any Appointment related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AppointmentRepository")
public interface AppointmentRepository extends BaseRepository<Appointment, Long> {
}
