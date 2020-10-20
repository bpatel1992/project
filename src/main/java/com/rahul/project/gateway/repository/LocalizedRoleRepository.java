package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedRole;
import org.springframework.stereotype.Repository;

/**
 * LocalizedRole Repository to handle any LocalizedRole related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedRoleRepository")
public interface LocalizedRoleRepository extends BaseRepository<LocalizedRole, LocalizedId> {
}
