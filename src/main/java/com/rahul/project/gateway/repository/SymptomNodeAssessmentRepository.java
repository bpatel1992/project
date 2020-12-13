package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.SymptomNodeAssessment;
import org.springframework.stereotype.Repository;

/**
 * SymptomNodeAssessment Repository to handle any SymptomNodeAssessment related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "SymptomNodeAssessmentRepository")
public interface SymptomNodeAssessmentRepository extends BaseRepository<SymptomNodeAssessment, Long> {
}
