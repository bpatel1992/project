package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedProfession;
import org.springframework.stereotype.Repository;

/**
 * LocalizedProfession Repository to handle any LocalizedProfession related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedProfessionRepository")
public interface LocalizedProfessionRepository extends BaseRepository<LocalizedProfession, LocalizedId> {
}
