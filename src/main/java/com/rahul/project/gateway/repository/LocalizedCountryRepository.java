package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedCountry;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedCountry Repository to handle any LocalizedCountry related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedCountryRepository")
public interface LocalizedCountryRepository extends BaseRepository<LocalizedCountry, LocalizedId> {
}
