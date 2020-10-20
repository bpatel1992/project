package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedStatusType;
import org.springframework.stereotype.Repository;

/**
 * LocalizedStatusType Repository to handle any LocalizedStatusType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedStatusTypeRepository")
public interface LocalizedStatusTypeRepository extends BaseRepository<LocalizedStatusType, LocalizedId> {
}
