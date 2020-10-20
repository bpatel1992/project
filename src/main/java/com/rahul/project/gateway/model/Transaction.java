package com.rahul.project.gateway.model;

import com.rahul.project.gateway.serialize.TransactionStatusSerializer;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "txn_m")
public class Transaction implements Serializable {

    private static final long serialVersionUID = -8547228405978266579L;

    @Id
    @SequenceGenerator(name = "txn_m_gen", allocationSize = 1, sequenceName = "txn_m_seq")
    @GeneratedValue(generator = "txn_m_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

//    @Column(name = "aggregator_reference_no", length = 40)
//    private String aggregatorReferenceNumber;

//    @ManyToOne
//    @Column(name = "STATUS", length = 3)
//    private StatusType status;


    @JoinColumn(name = "txn_status")
    private TransactionStatus status;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "fee")
    private BigDecimal fee;
    @Column(name = "tax")
    private BigDecimal tax;
    @Column(name = "payable_amount")
    private BigDecimal payableAmount;
    @Column(name = "service_ref_number")//appointment id
    private Long serviceReferenceNumber;
    //system generated transaction id
    @Column(name = "transaction_id", length = 30)
    private String transactionId;
    @Column(name = "transaction_Reference_id", length = 100)
    private String transactionGatewayReferenceId;
    @Column(name = "gateway_name", length = 30)
    private String gatewayName;
    @Column(name = "bank_name", length = 30)
    private String bankName;
    @Column(name = "is_reconciled", columnDefinition = "boolean default false", nullable = false)
    private Boolean isReconciled;
    @Column(name = "is_approved", columnDefinition = "boolean default false", nullable = false)
    private Boolean isApproved;
    @Column(name = "signature_img", length = 100)
    private String signatureImg;
    @Temporal(TemporalType.DATE)
    @Column(name = "txn_date")
    private Date date;
    @Temporal(TemporalType.TIME)
    @Column(name = "txn_time")
    private Date time;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "log_time")
    private Date logTime;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services services;
    @ManyToOne
    @JoinColumn(name = "payment_mode")
    private PaymentMode paymentMode;
    @ManyToOne
    // @JsonIgnoreProperties({"categoryListM", "departments", "addressM"})
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @Column(name = "url_redirect", length = 150)
    private String redirectUrl;
    @ManyToOne
    //@JsonIgnoreProperties({"fcmUmGcsM", "walletAccountM", "roles", "partners", "departments", "viewAlertses", "walletAccounts"})
    @JoinColumn(name = "customer_user_id")
    private User customerUserId;

    public enum TransactionStatusType {
        SUCCESS, FAILED, INPROGRESS,
    }

//    @ManyToOne
//    // @JsonIgnoreProperties({"categoryListM", "departments", "addressM"})
//    @JoinColumn(name = "to_partner_code")
//    private Partner toPartner;

//    @ManyToOne
////    @JsonIgnoreProperties({"fcmUmGcsM", "walletAccountM", "roles", "partners", "departments", "viewAlertses", "walletAccounts"})
//    @JoinColumn(name = "accepted_by_user_id")
//    private User acceptedByCustomerUserId;

    @JsonSerialize(using = TransactionStatusSerializer.class)
    public enum TransactionStatus {

        SUCCESS("SUCCESS", TransactionStatusType.SUCCESS),
        FAILED("FAILED", TransactionStatusType.FAILED),
        INPROGRESS("IN PROGRESS", TransactionStatusType.INPROGRESS);

        private final String name;

        private final Transaction.TransactionStatusType type;

        TransactionStatus(final String name,
                          final Transaction.TransactionStatusType type) {
            this.name = name;
            this.type = type;
        }

        public static List<Transaction.TransactionStatus> getTransactionStatusByTypes(
                List<Transaction.TransactionStatusType> transactionStatusTypes) {
            List<Transaction.TransactionStatus> transactionStatuses = new ArrayList<Transaction.TransactionStatus>();

            for (Transaction.TransactionStatus transactionStatus : Transaction.TransactionStatus
                    .values()) {
                if (transactionStatusTypes
                        .contains(transactionStatus.getType())) {
                    transactionStatuses.add(transactionStatus);
                }
            }

            return transactionStatuses;
        }

        public static List<Transaction.TransactionStatus> getTransactionStatusByType(
                Transaction.TransactionStatusType transactionStatusType) {
            return getTransactionStatusByTypes(Collections
                    .singletonList(transactionStatusType));
        }

        public static List<Transaction.TransactionStatus> getNotCancelledAppointmentStatuses() {
            return getTransactionStatusByTypes(Arrays.asList(
                    TransactionStatusType.SUCCESS,
                    TransactionStatusType.FAILED,
                    TransactionStatusType.INPROGRESS));
        }

        public String getName() {
            return this.name;
        }

        public Transaction.TransactionStatusType getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return name;
        }

    }


//    @ManyToOne
//    @JoinColumn(name = "txn_type")
//    private TransactionTypes transactionType;

    /*@ManyToOne
    @JsonIgnoreProperties("transactionTypes")
    @JoinColumn(name = "sub_txn_type")
    private SubTransactionTypesM subTransactionType;*/












   /* @ManyToOne
    @JoinColumn(name = "net_position_id")
    private NetPosition netPosition;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "sms_invoice_id")
    private SMSInvoiceM smsInvoice;

    @ManyToOne
    @JsonIgnoreProperties("txnId")
    @JoinColumn(name = "request_money_id")
    private RequestMoneyTxnM requestMoney;

    @ManyToOne
    @JoinColumn(name = "run_id")
    private SettlementRequest settlementRequest;*/

}
