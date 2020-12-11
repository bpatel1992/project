package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-25
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "partner_address")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerAddress implements Serializable {

    @ManyToMany
    @JoinTable(name = "partner_addresses_contact_mp", joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<PartnerContactNumber> partnerContactNumbers;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;
    @Basic
    @Column(name = "address")
    @Type(type = "text")
    private String address;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "displayOrder")
    private Integer displayOrder;
    @Basic
    @Column(name = "user_charges")
    private BigDecimal userCharges;
    @Basic
    @Column(name = "charges_slot_in_minutes")
    private Integer chargesSlotInMin;

    // clinic, private practice, hospital or Online Consultation
    @ManyToOne
    @JoinColumn(name = "address_type_id")
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnoreProperties("country")
    private State state;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties("state")
    private City city;

    @ManyToOne
    @JoinColumn(name = "pin_code_id")
    @JsonIgnoreProperties("city")
    private PINCode pinCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JsonInclude()
    @Transient
    private Set<BusinessTiming> businessTimings;

    public PartnerAddress(Long id) {
        this.id = id;
    }
}
