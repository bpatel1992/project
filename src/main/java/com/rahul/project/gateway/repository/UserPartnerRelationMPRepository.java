package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.CompositeId;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserPartnerRelationMP;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * UserPartnerRelationMP Repository to handle any UserPartnerRelationMP related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserPartnerRelationMPRepository")
public interface UserPartnerRelationMPRepository extends BaseRepository<UserPartnerRelationMP, CompositeId> {
    UserPartnerRelationMP getByUserAndAndPartner(User user, Partner partner);

    @Query("SELECT ur.partner FROM UserPartnerRelationMP ur WHERE ur.user.id = ?1 and ur.relation.relationName=?2")
    Set<Partner> byUserAndRelation(Long id, String relationName);

    @Query("SELECT ur FROM UserPartnerRelationMP ur WHERE ur.partner.id = ?1 order by ur.created desc")
    List<UserPartnerRelationMP> fetchCustomersByPartnerId(Long partnerId, Pageable pageable);
}
