package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.ReminderType;
import org.springframework.stereotype.Repository;

/**
 * ReminderType Repository to handle any ReminderType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "ReminderTypeRepository")
public interface ReminderTypeRepository extends BaseRepository<ReminderType, Long> {
}
