package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.WTDN;
import org.springframework.stereotype.Repository;

/**
 * WTDNRepository to handle any WTDN related Operations
 *
 * @author Rahul Malhotra
 */
@Repository
public interface WTDNRepository extends BaseRepository<WTDN, Long> {
}
