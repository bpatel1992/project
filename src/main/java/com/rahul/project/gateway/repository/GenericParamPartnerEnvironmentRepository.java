package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.GenericParamPartnerEnvironment;
import com.rahul.project.gateway.model.GenericParamPartnerEnvironmentIdClass;
import org.springframework.stereotype.Repository;

@Repository(value = "GenericParamPartnerEnvironmentRepository")
public interface GenericParamPartnerEnvironmentRepository extends BaseRepository<GenericParamPartnerEnvironment, GenericParamPartnerEnvironmentIdClass> {

}
