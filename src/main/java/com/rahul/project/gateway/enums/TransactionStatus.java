package com.rahul.project.gateway.enums;

import com.rahul.project.gateway.serialize.TransactionStatusSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonSerialize(using = TransactionStatusSerializer.class)
public enum TransactionStatus {

    SUCCESS("SUCCESS", TransactionStatusType.SUCCESS),
    FAILED("FAILED", TransactionStatusType.FAILED),
    INPROGRESS("IN PROGRESS", TransactionStatusType.INPROGRESS);

    private final String name;

    private final TransactionStatusType type;

    TransactionStatus(final String name,
                      final TransactionStatusType type) {
        this.name = name;
        this.type = type;
    }

    public static List<TransactionStatus> getTransactionStatusByTypes(
            List<TransactionStatusType> transactionStatusTypes) {

        List<TransactionStatus> transactionStatuses = new ArrayList<TransactionStatus>();

        for (TransactionStatus transactionStatus : TransactionStatus
                .values()) {
            if (transactionStatusTypes
                    .contains(transactionStatus.getType())) {
                transactionStatuses.add(transactionStatus);
            }
        }

        return transactionStatuses;
    }

    public static List<TransactionStatus> getTransactionStatusByType(
            TransactionStatusType transactionStatusType) {
        return getTransactionStatusByTypes(Collections
                .singletonList(transactionStatusType));
    }

    public static List<TransactionStatus> getNotCancelledAppointmentStatuses() {
        return getTransactionStatusByTypes(Arrays.asList(
                TransactionStatusType.SUCCESS,
                TransactionStatusType.FAILED,
                TransactionStatusType.INPROGRESS));
    }

    public String getName() {
        return this.name;
    }

    public TransactionStatusType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return name;
    }

}
