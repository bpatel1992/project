package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PaymentMode;
import org.springframework.stereotype.Repository;

@Repository(value = "PaymentModeRepository")
public interface PaymentModeRepository extends BaseRepository<PaymentMode, String> {

}
