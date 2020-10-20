package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedAssessmentOption;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedAssessmentOption Repository to handle any LocalizedAssessmentOption related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedAssessmentOptionRepository")
public interface LocalizedAssessmentOptionRepository extends BaseRepository<LocalizedAssessmentOption, LocalizedId> {
}
