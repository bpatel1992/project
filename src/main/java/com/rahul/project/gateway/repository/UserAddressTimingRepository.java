package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PartnerAddress;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserAddressTiming;
import org.springframework.stereotype.Repository;

/**
 * UserAddressTiming Repository to handle any UserAddressTiming related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserAddressTimingRepository")
public interface UserAddressTimingRepository extends BaseRepository<UserAddressTiming, Long> {
    UserAddressTiming getByUserAndPartnerAddress(User user, PartnerAddress partnerAddress);

    /*@Query(value = "SELECT ua.businessTimings FROM UserAddressTiming ua join ua.businessTimings.days bt " +
            "WHERE ua.user.id = ?1 and ua.partnerAddress.id = ?2 and " +
            "bt.code = ?3 ")
    Set<BusinessTiming> businessTimings(Long user, Long partnerAddress, int day);*/
}
