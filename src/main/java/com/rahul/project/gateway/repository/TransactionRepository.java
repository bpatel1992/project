package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Transaction Repository to handle any Transaction related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "TransactionRepository")
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    Transaction getByTransactionId(String txnId);

    @Query("select count(t),sum(t.amount),sum(t.earning) from Transaction t where t.status= 'S' ")
    List<Object> getTxnSummary();

    @Modifying
    @Query("update  Transaction t  set t.settlementRequest.settlementRunId =:runId where t.logTime <= :settlementTime and t.status= 'S' " +
            " and t.isApproved=false ")
    void updateTxnRunId(long runId, Date settlementTime);

    @Modifying
    @Query("update  Transaction t  set t.isApproved=true where t.settlementRequest.settlementRunId =:runId and t.status= 'S' " +
            " and t.isReconciled=true ")
    void updateTxnApprovedFlag(long runId);

    @Modifying
    @Query("update  Transaction t  set t.isReconciled=true where t.fromPartner.id =:gatewayPartnerCode " +
            " and t.settlementRequest.settlementRunId =:runId and t.status= 'S' " +
            " and t.isApproved=false ")
    void updateTxnReconcileFlagByGatewayPCode(long runId, long gatewayPartnerCode);

    @Modifying
    @Query("update  Transaction t  set t.isReconciled=true where t.fromPartner.id =:gatewayPartnerCode " +
            " and t.settlementRequest.settlementRunId =:runId and t.status= 'S' " +
            " and t.isApproved=false  and t.transactionId in :txnIds ")
    int updateTxnReconcileFlagByTxnType(long runId, long gatewayPartnerCode, List txnIds);

    @Modifying
    @Query("update Transaction t set t.isReconciled=true  where t.transactionId= :txn")
    Integer updateTxnReconcileFlag(String txn);

    @Modifying
    @Query("update Transaction t set t.isReconciled=true ,t.status =:txnStatus where t.transactionId= :txn")
    Integer updateTxnReconcileFlagAndStatus(String txn, String txnStatus);


    @Query("SELECT DISTINCT acceptedByCustomerUserId.firstName ,acceptedByCustomerUserId.id," +
            "acceptedByCustomerUserId.mobile,date ,time from Transaction where customerUserId.id= :id group by \n" +
            " acceptedByCustomerUserId.firstName ,acceptedByCustomerUserId.id,acceptedByCustomerUserId.mobile,date, time")
    List<Object> getRecentFundTransfers(Long id, Pageable pageable);

    @Query("select sum(t.amount)from Transaction t where t.status= 'S' and t.acceptedByCustomerUserId.id=:id")
    Double getTotalReceiveAmount(Long id);

    @Query("select sum(t.amount)from Transaction t where t.status= 'S' and t.customerUserId.id=:id")
    Double getTotalSpentAmount(Long id);
}
