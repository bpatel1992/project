package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Language;
import org.springframework.stereotype.Repository;

/**
 * Language Repository to handle any Language related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LanguageRepository")
public interface LanguageRepository extends BaseRepository<Language, String> {
}
