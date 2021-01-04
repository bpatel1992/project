package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.FeeM;
import org.springframework.stereotype.Repository;

@Repository(value = "FeeMRepository")
public interface FeeMRepository extends BaseRepository<FeeM, String> {

}
