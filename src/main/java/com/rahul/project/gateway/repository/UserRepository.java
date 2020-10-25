package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository to handle any User related operations
 *
 * @author Rahul Malhotra
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    @Query(value = "SELECT u.chargesSlotInMin FROM User u WHERE u.id= ?1")
    Integer getSlotTime(Long user);

    User getByRandomKey(String s);

    User getByUserName(String s);

    User getBySignUpRandomKey(String s);

    List<User> getByMobile(String aLong);

}
