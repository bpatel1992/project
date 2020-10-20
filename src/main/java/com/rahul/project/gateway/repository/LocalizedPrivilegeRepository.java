package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedPrivilege;
import org.springframework.stereotype.Repository;

/**
 * LocalizedPrivilege Repository to handle any LocalizedPrivilege related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedPrivilegeRepository")
public interface LocalizedPrivilegeRepository extends BaseRepository<LocalizedPrivilege, LocalizedId> {
}
