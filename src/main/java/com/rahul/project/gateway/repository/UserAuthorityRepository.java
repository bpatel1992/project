package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Authority;
import com.rahul.project.gateway.model.UserAuthority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * UserAuthority Repository to handle any UserAuthority related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserAuthorityRepository")
public interface UserAuthorityRepository extends BaseRepository<UserAuthority, Long> {
    @Query("SELECT ua.userAuthorityId.authority FROM UserAuthority ua WHERE ua.userAuthorityId.user.id = ?1")
    Set<Authority> byUserId(Long id);
}
