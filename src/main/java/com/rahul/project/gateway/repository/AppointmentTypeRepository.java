package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.AppointmentType;
import org.springframework.stereotype.Repository;

/**
 * AppointmentType Repository to handle any AppointmentType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AppointmentTypeRepository")
public interface AppointmentTypeRepository extends BaseRepository<AppointmentType, Long> {
}
