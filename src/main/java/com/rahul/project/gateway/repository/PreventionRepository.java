package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Prevention;
import org.springframework.stereotype.Repository;

/**
 * PreventionRepository to handle any Prevention related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PreventionRepository")
public interface PreventionRepository extends BaseRepository<Prevention, Long> {
}
