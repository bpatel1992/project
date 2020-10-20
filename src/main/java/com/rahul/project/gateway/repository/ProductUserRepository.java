package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.ProductUser;
import org.springframework.stereotype.Repository;

/**
 * ProductUserRepository to handle any ProductUser related operations
 *
 * @author Rahul Malhotra
 */
@Repository("ProductUserRepository")
public interface ProductUserRepository extends BaseRepository<ProductUser, Long> {

}
