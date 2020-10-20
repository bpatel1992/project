package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.UserAvailability;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * UserAvailability Repository to handle any UserAvailability related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserAvailabilityRepository")
public interface UserAvailabilityRepository extends BaseRepository<UserAvailability, Long> {
    @Query(value = "FROM UserAvailability ua WHERE ua.attendant.id = ?1 and ua.clinic.id = ?2 and " +
            "ua.fromTime >= ?3 AND " +
            "ua.toTime <= ?3  " +
            " ")
    List<UserAvailability> getByAttendantAndClinic(Long user, Long partnerAddress, Date date);


}
