package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedFunctionality;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedFunctionality Repository to handle any LocalizedFunctionality related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedFunctionalityRepository")
public interface LocalizedFunctionalityRepository extends BaseRepository<LocalizedFunctionality, LocalizedId> {
}
