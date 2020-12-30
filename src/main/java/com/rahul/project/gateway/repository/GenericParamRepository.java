package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.GenericParam;
import org.springframework.stereotype.Repository;

@Repository(value = "GenericParamRepository")
public interface GenericParamRepository extends BaseRepository<GenericParam, Long> {

}
