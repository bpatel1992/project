package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rahul.project.gateway.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


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

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    @ManyToOne
    @JsonIgnoreProperties({"categoryListM", "departments", "addressM"})
    @JoinColumn(name = "from_partner_code")
    private Partner fromPartner;

    @ManyToOne
    @JsonIgnoreProperties({"categoryListM", "departments", "addressM"})
    @JoinColumn(name = "to_partner_code")
    private Partner toPartner;

    @ManyToOne
    @JoinColumn(name = "txn_type")
    private TransactionTypes transactionType;

    @ManyToOne
    @JoinColumn(name = "service_package")
    private ServicePackage servicePackage;

    /*@ManyToOne
    @JsonIgnoreProperties("transactionTypes")
    @JoinColumn(name = "sub_txn_type")
    private SubTransactionTypesM subTransactionType;*/

    @Column(name = "aggregator_reference_no", length = 40)
    private String aggregatorReferenceNumber;

    @Column(name = "STATUS", length = 3)
    private TransactionStatus status;

    @ManyToOne
    @JsonIgnoreProperties({"fcmUmGcsM", "walletAccountM", "roles", "partners", "departments", "viewAlertses", "walletAccounts"})
    @JoinColumn(name = "customer_user_id")
    private User customerUserId;

    @ManyToOne
    @JsonIgnoreProperties({"fcmUmGcsM", "walletAccountM", "roles", "partners", "departments", "viewAlertses", "walletAccounts"})
    @JoinColumn(name = "accepted_by_user_id")
    private User acceptedByCustomerUserId;

    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "fee")
    private BigDecimal fee;
    @Column(name = "service_ref_number")//appointment id
    private Long serviceReferenceNumber;
    @Column(name = "tax")
    private BigDecimal tax;
    @Column(name = "payable_amount")
    private BigDecimal payableAmount;
    @Column(name = "merchant_fee")
    private BigDecimal merchantFee;

    @Column(name = "merchant_tax")
    private BigDecimal merchantTax;

    @Column(name = "transaction_id", length = 30)
    private String transactionId;

    @Column(name = "is_reconciled", columnDefinition = "boolean default false", nullable = false)
    private Boolean isReconciled;

    @Column(name = "is_approved", columnDefinition = "boolean default false", nullable = false)
    private Boolean isApproved;

    @Column(name = "txn_description", length = 100)
    private String txnDescription;

    @Column(name = "signature_img", length = 100)
    private String signatureImg;

    @ManyToOne
    @JoinColumn(name = "payment_mode")
    private PaymentMode paymentMode;

    @Temporal(TemporalType.DATE)
    @Column(name = "txn_date")
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "txn_time")
    private Date time;

    @Column(name = "log_time")
    private Date logTime;

    @ManyToOne
    @JoinColumn(name = "net_position_id")
    private NetPosition netPosition;

    @Column(name = "earning")
    private BigDecimal earning;

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

    @Column(name = "url_redirect", length = 150)
    private String redirectUrl;

    @ManyToOne
    @JoinColumn(name = "run_id")
    private SettlementRequest settlementRequest;

}
