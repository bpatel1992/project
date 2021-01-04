package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Currency;
import org.springframework.stereotype.Repository;

@Repository(value = "CurrencyRepository")
public interface CurrencyRepository extends BaseRepository<Currency, String> {

}
