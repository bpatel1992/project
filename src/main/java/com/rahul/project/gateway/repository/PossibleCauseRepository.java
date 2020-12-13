package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PossibleCause;
import org.springframework.stereotype.Repository;

/**
 * PossibleCauseRepository to handle any PossibleCause related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PossibleCauseRepository")
public interface PossibleCauseRepository extends BaseRepository<PossibleCause, Long> {
}
