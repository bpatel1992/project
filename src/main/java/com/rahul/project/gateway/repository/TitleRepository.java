package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Title;
import org.springframework.stereotype.Repository;

/**
 * Title Repository to handle any Title related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "TitleRepository")
public interface TitleRepository extends BaseRepository<Title, Long> {
}
