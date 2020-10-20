package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.SymptomNode;
import org.springframework.stereotype.Repository;

/**
 * SymptomNode Repository to handle any SymptomNode related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "SymptomNodeRepository")
public interface SymptomNodeRepository extends BaseRepository<SymptomNode, Long> {
}
