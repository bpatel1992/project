package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedFeature;
import com.rahul.project.gateway.model.LocalizedId;
import org.springframework.stereotype.Repository;

/**
 * LocalizedFeature Repository to handle any LocalizedFeature related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedFeatureRepository")
public interface LocalizedFeatureRepository extends BaseRepository<LocalizedFeature, LocalizedId> {
}
