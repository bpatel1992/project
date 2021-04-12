package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.UserPetRelationMP;
import com.rahul.project.gateway.model.UserPetRelationMpPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserPetRelationMP Repository to handle any UserPetRelationMP related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserPetRelationMPRepository")
public interface UserPetRelationMPRepository extends BaseRepository<UserPetRelationMP, UserPetRelationMpPK> {

    @Query("SELECT upr FROM UserPetRelationMP upr WHERE upr.id.user.id = ?1 and upr.id.pet.id = ?2")
    UserPetRelationMP getUserPetRelation(Long customerId, Long petId);

    @Query("SELECT upr FROM UserPetRelationMP upr WHERE upr.id.user.id = ?1")
    List<UserPetRelationMP> getUserPetRelation(Long customerId);
}
