package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Environment;
import org.springframework.stereotype.Repository;

@Repository(value = "EnvironmentRepository")
public interface EnvironmentRepository extends BaseRepository<Environment, Long> {

}
