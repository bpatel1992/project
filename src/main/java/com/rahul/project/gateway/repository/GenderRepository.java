package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Gender;
import org.springframework.stereotype.Repository;

/**
 * Gender Repository to handle any Gender related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "GenderRepository")
public interface GenderRepository extends BaseRepository<Gender, Long> {
}
