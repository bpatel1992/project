package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Transaction;
import org.springframework.stereotype.Repository;

/**
 * Transaction Repository to handle any Transaction related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "TransactionRepository")
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    Transaction getByTransactionId(String txnId);
}
