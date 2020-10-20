package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.City;
import org.springframework.stereotype.Repository;

/**
 * City Repository to handle any City related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "CityRepository")
public interface CityRepository extends BaseRepository<City, Long> {
}
