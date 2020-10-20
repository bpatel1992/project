package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.State;
import org.springframework.stereotype.Repository;

/**
 * State Repository to handle any State related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "StateRepository")
public interface StateRepository extends BaseRepository<State, Long> {
}
