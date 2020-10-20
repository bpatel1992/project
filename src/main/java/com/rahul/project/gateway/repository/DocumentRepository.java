package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Document;
import org.springframework.stereotype.Repository;

/**
 * Document Repository to handle any Document related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "DocumentRepository")
public interface DocumentRepository extends BaseRepository<Document, Long> {
}
