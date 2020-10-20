package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Gallery;
import org.springframework.stereotype.Repository;

/**
 * Gallery Repository to handle any Gallery related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "GalleryRepository")
public interface GalleryRepository extends BaseRepository<Gallery, Long> {
}
