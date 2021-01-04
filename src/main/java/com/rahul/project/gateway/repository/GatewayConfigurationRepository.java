package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.GatewayConfiguration;
import org.springframework.stereotype.Repository;

@Repository(value = "GatewayConfigurationRepository")
public interface GatewayConfigurationRepository extends BaseRepository<GatewayConfiguration, String> {

}
