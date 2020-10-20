package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedSymptom;
import org.springframework.stereotype.Repository;

/**
 * LocalizedSymptom Repository to handle any LocalizedSymptom related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedSymptomRepository")
public interface LocalizedSymptomRepository extends BaseRepository<LocalizedSymptom, LocalizedId> {
}
