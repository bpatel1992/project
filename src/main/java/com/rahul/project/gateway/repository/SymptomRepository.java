package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Symptom;
import org.springframework.stereotype.Repository;

/**
 * SymptomRepository to handle any Symptom related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "SymptomRepository")
public interface SymptomRepository extends BaseRepository<Symptom, Long> {
}
