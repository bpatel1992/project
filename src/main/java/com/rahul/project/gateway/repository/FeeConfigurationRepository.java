package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.FeeConfiguration;
import org.springframework.stereotype.Repository;

@Repository(value = "FeeConfigurationRepository")
public interface FeeConfigurationRepository extends BaseRepository<FeeConfiguration, String> {

}
