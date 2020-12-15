package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "gateway_tax_fee_configuration_m")
public class GatewayConfiguration implements Serializable {

    private static final long serialVersionUID = 6768499208276381197L;

    /*@EmbeddedId
    private GatewayConfigurationId gatewayConfiguration;*/

    @Id
    @SequenceGenerator(name = "gateway_tax_fee_configuration_m_gen", allocationSize = 1,
            sequenceName = "gateway_tax_fee_configuration_m_seq")
    @GeneratedValue(generator = "gateway_tax_fee_configuration_m_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"departments", "addressM", "walletAccountM", "tenants", "categoryListM"})
    @JoinColumn(name = "from_partner")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "payment_mode")
    private PaymentMode paymentMode;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    @ManyToMany
    @JoinTable(name = "gateway_configuration_fee_mp"
            , joinColumns = {@JoinColumn(name = "configuration_id", referencedColumnName = "id")}
//            , @JoinColumn(name = "partner_id", referencedColumnName = "from_partner")}
            , inverseJoinColumns = @JoinColumn(name = "fee_id"))
    private Set<FeeM> partnerFees;

    @ManyToMany
    @JoinTable(name = "gateway_configuration_tax_mp"
            , joinColumns = {@JoinColumn(name = "configuration_id", referencedColumnName = "id")}
//            , @JoinColumn(name = "partner_id", referencedColumnName = "from_partner")}
            , inverseJoinColumns = @JoinColumn(name = "tax_id"))
    private Set<TaxM> partnerTaxes;

}
