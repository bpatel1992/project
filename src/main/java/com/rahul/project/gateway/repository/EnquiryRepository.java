package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Enquiry;
import org.springframework.stereotype.Repository;

/**
 * Enquiry Repository to handle any Enquiry related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "EnquiryRepository")
public interface EnquiryRepository extends BaseRepository<Enquiry, Long> {
}
