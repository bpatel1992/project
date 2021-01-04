package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.TransactionTypes;
import org.springframework.stereotype.Repository;

@Repository(value = "TransactionTypesRepository")
public interface TransactionTypesRepository extends BaseRepository<TransactionTypes, String> {

}
