package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "partner_m")
public class Partner implements Serializable {
    @Id
    @SequenceGenerator(name = "partner_m_gen", allocationSize = 1, sequenceName = "partner_m_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_m_gen")
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "bank_name")
    private String bankName;

    @JoinColumn(name = "ifsc")
    private String ifsc;

    @JoinColumn(name = "bank_account_number")
    private String bankAccountNumber;

    @Basic
    @Column(name = "partner_type")
    private String partnerType;
    @ManyToOne
    @JoinColumn(name = "title_id", referencedColumnName = "id")
    private Title title;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "activated", columnDefinition = "boolean default false")
    private Boolean activated;
    @Basic
    @Column(name = "subscribed", columnDefinition = "boolean default false")
    private Boolean subscribed;
    @Basic
    @Column(name = "user_name", unique = true)
    private String userName;
    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private Profession profession;
    @Basic
    @Column(name = "address")
    @Type(type = "text")
    private String address;
    @Basic
    @Column(name = "logo")
    private String logo;
    @ManyToOne
    @JoinColumn(name = "mobile_country_id", referencedColumnName = "id")
    private Country country;
    @Basic
    @Column(name = "mobile")
    private String mobile;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "business_name")
    private String businessName;
    @Basic
    @Column(name = "partner_experience")
    private Float partnerExperience;
    @Basic
    @Column(name = "fbLink")
    private String fbLink;//": "",
    @Basic
    @Column(name = "youtubeLink")
    private String youtubeLink;//": "",
    @Basic
    @Column(name = "instagramLink")
    private String instagramLink;//": ""
    @Basic
    @Column(name = "twitterLink")// ,
    private String twitterLink;//": "",
    @Basic
    @Column(name = "partner_desc")
    private String partnerDesc;
    @Basic
    @Column(name = "e_card_avatar")
    private String eCardAvatar;
    @Basic
    @Column(name = "e_card_cover_image")
    private String eCardCoverImage;
    @Column(name = "status", columnDefinition = "boolean default false", nullable = false)
    private boolean status;
    @ManyToMany()
    @JoinTable(name = "partner_address_partner4", joinColumns = @JoinColumn(name = "partner_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<PartnerAddress> partnerAddresses;
    @ManyToMany()
    @JoinTable(name = "partner_type_mp", joinColumns = @JoinColumn(name = "partner_id"),
            inverseJoinColumns = @JoinColumn(name = "partner_type_id"))
    private Set<PartnerTypeM> partnerTypes;
    @ManyToMany()
    @JoinTable(name = "user_partner_relation_mp", joinColumns = @JoinColumn(name = "partner_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties("partners")
    private Set<User> users;

    public Partner(Long id) {
        this.id = id;
    }
}
