package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedPossibleCausePriority;
import org.springframework.stereotype.Repository;

/**
 * LocalizedPossibleCausePriority Repository to handle any LocalizedPossibleCausePriority related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedPossibleCausePriorityRepository")
public interface LocalizedPossibleCausePriorityRepository extends BaseRepository<LocalizedPossibleCausePriority, LocalizedId> {
}
