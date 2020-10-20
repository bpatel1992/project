package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedPrevention;
import org.springframework.stereotype.Repository;

/**
 * LocalizedPrevention Repository to handle any LocalizedPrevention related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedPreventionRepository")
public interface LocalizedPreventionRepository extends BaseRepository<LocalizedPrevention, LocalizedId> {
}
