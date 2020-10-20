package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.AssessmentOption;
import org.springframework.stereotype.Repository;

/**
 * AssessmentOption Repository to handle any AssessmentOption related Operations
 *
 * @author Rahul Malhotra
 */
@Repository
public interface AssessmentOptionRepository extends BaseRepository<AssessmentOption, Long> {
}
