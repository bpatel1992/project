package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.UserLoginType;
import org.springframework.stereotype.Repository;

/**
 * UserLoginType Repository to handle any UserLoginType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserLoginTypeRepository")
public interface UserLoginTypeRepository extends BaseRepository<UserLoginType, Long> {
    UserLoginType getByLoginType(String s);
}
