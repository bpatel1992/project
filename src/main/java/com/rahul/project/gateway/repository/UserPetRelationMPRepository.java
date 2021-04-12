package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.UserPetRelationMP;
import com.rahul.project.gateway.model.UserPetRelationMpPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying      // to mark delete or update query
    @Query("DELETE FROM UserPetRelationMP upr WHERE upr.id.user.id = :customerId and upr.id.pet.id =:petId")
    int delete(@Param("customerId") Long customerId, @Param("petId") Long petId);

    @Query("SELECT upr FROM UserPetRelationMP upr WHERE upr.id.user.id = ?1")
    List<UserPetRelationMP> getUserPetRelation(Long customerId);
}
