package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.WeightUnit;
import org.springframework.stereotype.Repository;

/**
 * WeightUnit Repository to handle any WeightUnit related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "WeightUnitRepository")
public interface WeightUnitRepository extends BaseRepository<WeightUnit, Long> {
}
