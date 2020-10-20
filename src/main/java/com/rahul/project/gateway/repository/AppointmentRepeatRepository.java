package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.AppointmentRepeat;
import org.springframework.stereotype.Repository;

/**
 * AppointmentRepeat Repository to handle any AppointmentRepeat related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AppointmentRepeatRepository")
public interface AppointmentRepeatRepository extends BaseRepository<AppointmentRepeat, Long> {
}
