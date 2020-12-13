package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PossibleCauseFeedback;
import org.springframework.stereotype.Repository;

/**
 * PossibleCauseFeedback Repository to handle any PossibleCauseFeedback related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PossibleCauseFeedbackRepository")
public interface PossibleCauseFeedbackRepository extends BaseRepository<PossibleCauseFeedback, Long> {
}
