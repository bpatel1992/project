package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Privilege;
import org.springframework.stereotype.Repository;

/**
 * PrivilegeRepository to handle any Privilege related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PrivilegeRepository")
public interface PrivilegeRepository extends BaseRepository<Privilege, Long> {

    Privilege findByPrivilege(String privilege);
}
