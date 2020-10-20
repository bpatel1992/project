package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Certificate;
import org.springframework.stereotype.Repository;

/**
 * Certificate Repository to handle any Certificate related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "CertificateRepository")
public interface CertificateRepository extends BaseRepository<Certificate, Long> {
}
