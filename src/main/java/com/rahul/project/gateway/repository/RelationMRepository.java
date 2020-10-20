package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.RelationM;
import org.springframework.stereotype.Repository;

/**
 * RelationM Repository to handle any RelationM related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "RelationMRepository")
public interface RelationMRepository extends BaseRepository<RelationM, Long> {

    RelationM getByRelationName(String relationName);
}
