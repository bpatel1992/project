package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedLanguage;
import org.springframework.stereotype.Repository;

/**
 * LocalizedLanguage Repository to handle any LocalizedLanguage related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedLanguageRepository")
public interface LocalizedLanguageRepository extends BaseRepository<LocalizedLanguage, LocalizedId> {
}
