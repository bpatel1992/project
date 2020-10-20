package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Authority;
import org.springframework.stereotype.Repository;

/**
 * Authority Repository to handle any Authority related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AuthorityRepository")
public interface AuthorityRepository extends BaseRepository<Authority, Long> {
    Authority getByName(String s);
}
