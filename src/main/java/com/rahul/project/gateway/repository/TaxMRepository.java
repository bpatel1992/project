package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.TaxM;
import org.springframework.stereotype.Repository;

@Repository(value = "TaxMRepository")
public interface TaxMRepository extends BaseRepository<TaxM, String> {

}
