package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedWTDN;
import org.springframework.stereotype.Repository;

/**
 * LocalizedWTDN Repository to handle any LocalizedWTDN related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedWTDNRepository")
public interface LocalizedWTDNRepository extends BaseRepository<LocalizedWTDN, LocalizedId> {
}
