package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Assessment;
import org.springframework.stereotype.Repository;

/**
 * AssessmentRepository to handle any Assessment related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AssessmentRepository")
public interface AssessmentRepository extends BaseRepository<Assessment, Long> {
}
