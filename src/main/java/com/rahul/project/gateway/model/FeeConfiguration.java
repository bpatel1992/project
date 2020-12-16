package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fee_configuration_m")
public class FeeConfiguration implements Serializable {

    private static final long serialVersionUID = -6405322582674459512L;

    @Id
    @SequenceGenerator(name = "fee_configuration_m_gen", sequenceName = "fee_configuration_m_seq")
    @GeneratedValue(generator = "fee_configuration_m_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"departments", "addressM", "walletAccountM", "tenants", "categoryListM"})
    @JoinColumn(name = "from_partner")
    private Partner fromPartner;

    @ManyToOne
    @JoinColumn(name = "merchant_category")
    private MerchantCategory merchantCategory;

    @ManyToOne
    @JoinColumn(name = "transaction_type")
    private TransactionTypes transactionType;

    @ManyToOne
    @JoinColumn(name = "payment_mode")
    private PaymentMode paymentMode;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    /*@ManyToMany
    @JoinTable(name = "from_partner_configuration_fee_mp"
            , joinColumns = {@JoinColumn(name = "payment_mode", referencedColumnName = "payment_mode")
            , @JoinColumn(name = "partner_id", referencedColumnName = "from_partner")}
            , inverseJoinColumns = @JoinColumn(name = "fee_id"))
    private Set<FeeM> fromPartnerFees;*/

    @ManyToMany
    @JoinTable(name = "merchant_category_configuration_fee_mp"
            , joinColumns = {@JoinColumn(name = "configuration_id", referencedColumnName = "id")}
//			, @JoinColumn(name = "merchant_category", referencedColumnName = "merchant_category")}
            , inverseJoinColumns = @JoinColumn(name = "fee_id"))
    private Set<FeeM> operatorFees;

    /*@ManyToMany
    @JoinTable(name = "from_partner_configuration_tax_mp"
            , joinColumns = {@JoinColumn(name = "payment_mode", referencedColumnName = "payment_mode")
            , @JoinColumn(name = "partner_id", referencedColumnName = "from_partner")}
            , inverseJoinColumns = @JoinColumn(name = "tax_id"))
    private Set<TaxM> fromPartnerTaxes;*/

    @ManyToMany
    @JoinTable(name = "merchant_category_configuration_tax_mp"
            , joinColumns = {@JoinColumn(name = "configuration_id", referencedColumnName = "id")}
//            , @JoinColumn(name = "merchant_category", referencedColumnName = "merchant_category")}
            , inverseJoinColumns = @JoinColumn(name = "tax_id"))
    private Set<TaxM> operatorTaxes;
}
