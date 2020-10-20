package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Reminder;
import org.springframework.stereotype.Repository;

/**
 * Reminder Repository to handle any Reminder related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "ReminderRepository")
public interface ReminderRepository extends BaseRepository<Reminder, Long> {
}
