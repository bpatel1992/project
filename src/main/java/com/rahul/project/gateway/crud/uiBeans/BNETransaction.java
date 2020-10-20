package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;

@Getter
@Setter
@UIBeanSpecifier(id = "1", beanClass = BNETransaction.class, keys = {"id", "status"})
public class BNETransaction extends BNEBase {

    public String status;
    private long txnId;
    private String serviceName;
    private String transactionTypeName;
    private String transactionGatewayReferenceId;
    private long customerUserId;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private long acceptedByCustomerUserId;
    private String acceptedByCustomerFirstName;
    private String acceptedByCustomerLastName;
    private String acceptedByCustomerPhone;
    private Double amount;
    private String transactionId;
    private String date;
    private String time;
    private String logTime;
    private boolean isSettled;

    public BNETransaction(Transaction transaction) {
        this.status = transaction.getStatus().getName();
        this.txnId = transaction.getId();
        this.transactionGatewayReferenceId = transaction.getTransactionGatewayReferenceId() != null ? transaction.getTransactionGatewayReferenceId() : "";
        this.amount = transaction.getAmount().doubleValue();
        this.transactionId = transaction.getTransactionId();
        this.date = transaction.getDate().toString();
        this.time = transaction.getTime().toString();
        this.isSettled = transaction.getIsApproved();
        SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-MM-yyyy HH:mm aa");
        this.logTime = transaction.getLogTime() != null ? myFormatObj.format(transaction.getLogTime()) : "";

    }
}
