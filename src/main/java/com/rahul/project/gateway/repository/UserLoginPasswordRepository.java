package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserLoginPassword;
import com.rahul.project.gateway.model.UserLoginType;
import org.springframework.stereotype.Repository;

/**
 * UserLoginPassword Repository to handle any UserLoginPassword related Operations
 *
 * @author Rahul Malhotra
 */
@Repository
public interface UserLoginPasswordRepository extends BaseRepository<UserLoginPassword, Long> {
    UserLoginPassword getByUserAndLoginType(User user, UserLoginType userLoginType);
}
