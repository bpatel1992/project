package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Day;
import org.springframework.stereotype.Repository;

/**
 * Day Repository to handle any Day related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "DayRepository")
public interface DayRepository extends BaseRepository<Day, Long> {
}
