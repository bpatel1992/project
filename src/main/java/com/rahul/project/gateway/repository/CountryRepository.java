package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Country;
import org.springframework.stereotype.Repository;

/**
 * CountryDTO Repository to handle any CountryDTO related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "CountryRepository")
public interface CountryRepository extends BaseRepository<Country, Long> {
}
