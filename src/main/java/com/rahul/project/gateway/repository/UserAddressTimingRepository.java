package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * UserAddressTiming Repository to handle any UserAddressTiming related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserAddressTimingRepository")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {NullPointerException.class, Exception.class, Throwable.class}
        , isolation = Isolation.READ_COMMITTED
/* , noRollbackFor= {CustomException.class} */)
public interface UserAddressTimingRepository extends BaseRepository<UserAddressTiming, Long> {
    UserAddressTiming getByUserAndPartnerAddress(User user, PartnerAddress partnerAddress);

    @Query(value = "SELECT ua FROM UserAddressTiming ua WHERE ua.user.id = ?1 " +
            " and ua.partnerAddress.id = ?2")
    UserAddressTiming getUserAddressTiming(Long userId, Long partnerAddressId);

    @Query(value = "SELECT ua.partnerAddress FROM UserAddressTiming ua WHERE ua.user.id = ?1 ")
    Set<PartnerAddress> getPartnerAddress(Long userId);

    @Query(value = "SELECT bt.timeRange FROM UserAddressTiming ua join ua.businessTimings bt " +
            " join bt.days dy WHERE ua.user.id = ?1 and ua.partnerAddress.id = ?2 and " +
            "dy.code = ?3 ")
    Set<TimeRange> getTimeRange(Long user, Long partnerAddress, String day);

    @Query(value = "SELECT bt FROM UserAddressTiming ua join ua.businessTimings bt " +
            " WHERE ua.user.id = ?1 and ua.partnerAddress.id = ?2 ")
    Set<BusinessTiming> businessTimingsByUserIdAndPartnerAddressId(Long partnerId, Long partnerAddressId);

    @Query(value = "SELECT bt FROM UserAddressTiming ua join ua.businessTimings bt join bt.days dy" +
            " WHERE ua.user.id = ?1 and dy.code = ?2 ")
    Set<BusinessTiming> businessTimingsByUserId(Long partnerId, String day);
}
