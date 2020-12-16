package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "SMSINVOICEM_SEQ", allocationSize = 1, sequenceName = "SMSINVOICEM_SEQ")
@Table(name = "SMS_INVOICES_M")
@NoArgsConstructor
public class SMSInvoiceM implements Serializable {

    private static final long serialVersionUID = 802482301415978769L;
    @Id
    @GeneratedValue(generator = "SMSINVOICEM_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "SMS_INVOICE_ID")
    private long smsInvoiceID;
    @ManyToOne
    @JoinColumn(name = "TXN_USER_ID")
    private User customerUser;
    @ManyToOne
    @JoinColumn(name = "TXN_SEQUENCENO")
    private Transaction transactionM;
    @Column(name = "CUSTOMER_MOBILE_NUMBER")
    private BigDecimal customerMobileNumber;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "CUSTOMER_EMAIL")
    private String customerEmail;
    @Column(name = "SMS_INVOICE_DESCRIPTION")
    private String smsInvoiceDescription;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "INVOICE_REFERENCE_ID")
    private String invoiceReferenceID;
    @Column(name = "ADDITIONAL_MOBILE_NUMBER")
    private BigDecimal aditionalMobileNumber;
    @Column(name = "SEND_DATE")
//	@Temporal(TemporalType.TIMESTAMP)
    private Timestamp sendDate;
    @Column(name = "EXPIRY_DATE")
//	@Temporal(TemporalType.TIMESTAMP)
    private Timestamp expiryDate;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar paymentDate;
    @Column(name = "SMS_INVOICE_STATUS")
    private String smsInvoiceStatus;
    @Column(name = "RANDOM_KEY")
    private String randomKey;
    @Column(name = "TXN_TYPE")
    private String txnType;
    @Column(name = "multiple_payment_enabled", columnDefinition = "boolean default false", nullable = false)
    private boolean multiplePaymentEnabled;
    @ManyToOne
    @JsonIgnoreProperties({"departments", "addressM", "walletAccountM", "tenants", "categoryListM"})
    @JoinColumn(name = "BOARDING_PARTNER_CODE")
    private Partner merchantPartner;

    public SMSInvoiceM(long id) {
        this.smsInvoiceID = id;
    }

}
