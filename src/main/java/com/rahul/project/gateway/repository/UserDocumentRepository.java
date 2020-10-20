package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.UserDocument;
import org.springframework.stereotype.Repository;

/**
 * UserDocument Repository to handle any UserDocument related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "UserDocumentRepository")
public interface UserDocumentRepository extends BaseRepository<UserDocument, Long> {
    UserDocument getByDocumentNumber(String s);
}
