package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedAssessment;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedAssessment Repository to handle any LocalizedAssessment related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedAssessmentRepository")
public interface LocalizedAssessmentRepository extends BaseRepository<LocalizedAssessment, LocalizedId> {
}
