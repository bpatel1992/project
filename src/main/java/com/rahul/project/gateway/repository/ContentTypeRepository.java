package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.ContentType;
import org.springframework.stereotype.Repository;

/**
 * ContentType Repository to handle any ContentType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "ContentTypeRepository")
public interface ContentTypeRepository extends BaseRepository<ContentType, Long> {
}
