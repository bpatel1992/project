package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.GcsgenericParamsM;
import org.springframework.stereotype.Repository;

@Repository(value = "GcsgenericParamsMRepository")
public interface GcsgenericParamsMRepository extends BaseRepository<GcsgenericParamsM, Long> {

}
