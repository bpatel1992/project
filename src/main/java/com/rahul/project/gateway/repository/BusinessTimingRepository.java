package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.BusinessTiming;
import org.springframework.stereotype.Repository;

/**
 * BusinessTiming Repository to handle any BusinessTiming related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "BusinessTimingRepository")
public interface BusinessTimingRepository extends BaseRepository<BusinessTiming, Long> {
}
