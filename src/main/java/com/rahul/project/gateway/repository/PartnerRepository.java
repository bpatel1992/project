package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Partner;
import org.springframework.stereotype.Repository;

@Repository(value = "PartnerRepository")
public interface PartnerRepository extends BaseRepository<Partner, Long> {
    Partner getByUserName(String userName);
}
