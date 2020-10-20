package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedGender;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedGender Repository to handle any LocalizedGender related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedGenderRepository")
public interface LocalizedGenderRepository extends BaseRepository<LocalizedGender, LocalizedId> {
}
