package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedAppointmentType;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedAppointmentType Repository to handle any LocalizedAppointmentType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedAppointmentTypeRepository")
public interface LocalizedAppointmentTypeRepository extends BaseRepository<LocalizedAppointmentType, LocalizedId> {
}
