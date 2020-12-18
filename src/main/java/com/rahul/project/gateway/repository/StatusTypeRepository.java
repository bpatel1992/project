package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.StatusType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * ServiceType Repository to handle any ServiceType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "StatusTypeRepository")
public interface StatusTypeRepository extends BaseRepository<StatusType, Long> {
    @Query("SELECT s FROM StatusType s WHERE s.StatusTypeName = ?1")
    StatusType findByStatusTypeName(String statusTypeName);

}
