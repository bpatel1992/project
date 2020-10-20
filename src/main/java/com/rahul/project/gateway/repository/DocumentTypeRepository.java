package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.DocumentType;
import org.springframework.stereotype.Repository;

/**
 * DocumentType Repository to handle any DocumentType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "DocumentTypeRepository")
public interface DocumentTypeRepository extends BaseRepository<DocumentType, Long> {
    DocumentType getByDocumentType(String s);
}
