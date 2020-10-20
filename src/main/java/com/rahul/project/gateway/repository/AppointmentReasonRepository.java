package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.AppointmentReason;
import org.springframework.stereotype.Repository;

/**
 * AppointmentReason Repository to handle any AppointmentReason related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AppointmentReasonRepository")
public interface AppointmentReasonRepository extends BaseRepository<AppointmentReason, Long> {
}
