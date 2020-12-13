package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Functionality;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository to handle any role related operations
 *
 * @author Rahul Malhotra
 * @Date : 21-May-2019 at 4:20:57 pm
 */
@Repository(value = "FunctionalityRepository")
public interface FunctionalityRepository extends BaseRepository<Functionality, Long> {

}
