package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PossibleCausePriority;
import org.springframework.stereotype.Repository;

/**
 * PossibleCausePriority Repository to handle any PossibleCausePriority related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PossibleCausePriorityRepository")
public interface PossibleCausePriorityRepository extends BaseRepository<PossibleCausePriority, Long> {
}
