package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Remedy;
import org.springframework.stereotype.Repository;

/**
 * HomeRemedyRepository to handle any Remedy related Operations
 *
 * @author Rahul Malhotra
 */
@Repository
public interface RemedyRepository extends BaseRepository<Remedy, Long> {
}
