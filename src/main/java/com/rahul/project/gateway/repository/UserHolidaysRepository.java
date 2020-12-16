package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.UserHolidays;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * UserHolidays Repository to handle any UserHolidays related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserHolidaysRepository")
public interface UserHolidaysRepository extends BaseRepository<UserHolidays, Long> {
    @Query(value = "FROM UserHolidays ua WHERE ua.attendant.id = ?1 and ua.clinic.id = ?2 and " +
            "ua.fromTime >= ?3 AND " +
            "ua.toTime <= ?3  " +
            " ")
    List<UserHolidays> getByAttendantAndClinic(Long user, Long partnerAddress, Date date);

    @Query(value = "FROM UserHolidays ua WHERE ua.attendant.id = ?1 and " +
            "?2 between ua.fromTime AND ua.toTime ")
    List<UserHolidays> getByAttendant(Long user, Date date);


}
