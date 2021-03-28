package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Services;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserServiceStatus;
import org.springframework.stereotype.Repository;

/**
 * UserServiceStatusRepository to handle any UserServiceStatus related operations
 *
 * @author Rahul Malhotra
 * @Date : 25-Feb-2019 at 2:33:57 pm
 */
@Repository(value = "UserServiceStatusRepository")
public interface UserServiceStatusRepository extends BaseRepository<UserServiceStatus, Long> {

    UserServiceStatus getDistinctByUserIdAndServiceId(User user, Services service);

}
