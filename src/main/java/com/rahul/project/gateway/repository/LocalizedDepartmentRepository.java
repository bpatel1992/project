package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedDepartment;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedDepartment Repository to handle any LocalizedDepartment related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedDepartmentRepository")
public interface LocalizedDepartmentRepository extends BaseRepository<LocalizedDepartment, LocalizedId> {
}
