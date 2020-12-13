package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.RemedyType;
import org.springframework.stereotype.Repository;

/**
 * RemedyTypeRepository to handle any RemedyType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "RemedyTypeRepository")
public interface RemedyTypeRepository extends BaseRepository<RemedyType, Long> {
}
