package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PartnerAddress;
import com.rahul.project.gateway.model.TimeRange;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserAddressTiming;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * UserAddressTiming Repository to handle any UserAddressTiming related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserAddressTimingRepository")
public interface UserAddressTimingRepository extends BaseRepository<UserAddressTiming, Long> {
    UserAddressTiming getByUserAndPartnerAddress(User user, PartnerAddress partnerAddress);

    @Query(value = "SELECT bt.timeRange FROM UserAddressTiming ua join ua.businessTimings bt " +
            " join bt.days dy WHERE ua.user.id = ?1 and ua.partnerAddress.id = ?2 and " +
            "dy.code = ?3 ")
    Set<TimeRange> businessTimings(Long user, Long partnerAddress, String day);
}
