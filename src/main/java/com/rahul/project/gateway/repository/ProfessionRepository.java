package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Profession;
import org.springframework.stereotype.Repository;

/**
 * Profession Repository to handle any Profession related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "ProfessionRepository")
public interface ProfessionRepository extends BaseRepository<Profession, Long> {
}
