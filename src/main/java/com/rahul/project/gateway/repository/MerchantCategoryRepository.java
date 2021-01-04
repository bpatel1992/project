package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.MerchantCategory;
import org.springframework.stereotype.Repository;

@Repository(value = "MerchantCategoryRepository")
public interface MerchantCategoryRepository extends BaseRepository<MerchantCategory, String> {

}
