package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedPetCondition;
import org.springframework.stereotype.Repository;

/**
 * LocalizedPetCondition Repository to handle any LocalizedPetCondition related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedPetConditionRepository")
public interface LocalizedPetConditionRepository extends BaseRepository<LocalizedPetCondition, LocalizedId> {
}
