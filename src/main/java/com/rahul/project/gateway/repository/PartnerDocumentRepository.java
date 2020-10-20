package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PartnerDocument;
import org.springframework.stereotype.Repository;

/**
 * PartnerDocument Repository to handle any PartnerDocument related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PartnerDocumentRepository")
public interface PartnerDocumentRepository extends BaseRepository<PartnerDocument, Long> {
    PartnerDocument getByDocumentNumber(String s);
}
