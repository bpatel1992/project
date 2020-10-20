package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PINCode;
import org.springframework.stereotype.Repository;

/**
 * PINCode Repository to handle any PINCode related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PINCodeRepository")
public interface PINCodeRepository extends BaseRepository<PINCode, Long> {
}
