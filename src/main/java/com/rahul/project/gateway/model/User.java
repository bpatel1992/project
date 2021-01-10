package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Basic
    @Column(name = "activated", columnDefinition = "boolean default false", nullable = false)
    private Boolean activated;
    @Basic
    @Column(name = "subscribed", columnDefinition = "boolean default false")
    private Boolean subscribed;
    @Basic
    @Column(name = "user_name", unique = true)
    private String userName;
    // in months
    @Basic
    @Column(name = "user_experience")
    private Integer userExperience;
    @Basic
    @Column(name = "user_charges")
    private BigDecimal userCharges;
    @Basic
    @Column(name = "charges_slot_in_minutes")
    private Integer chargesSlotInMin;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Basic
    @Column(name = "avatar_name")
    private String avatarName;
    @Basic
    @Column(name = "cover_name")
    private String coverName;
    @Basic
    @Column(name = "image_url")
    private String profileImage;
    @Basic
    @Column(name = "random_key")
    private String randomKey;
    @Basic
    @Column(name = "sign_up_random_key")
    private String signUpRandomKey;
    @Basic
    @Column(name = "user_type", length = 5)
    private String userType;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "full_name")
    private String fullName;
    @Basic
    @Column(name = "registration_number")
    private String registrationNumber;
    @Basic
    @Column(name = "registration_council")
    private String registrationCouncil;
    @Basic
    @Column(name = "registration_year")
    private String registrationYear;
    @Basic
    @Column(name = "degree")
    private String degree;
    @Basic
    @Type(type = "text")
    @Column(name = "award_recognition")
    private String awardRecognition;
    @JoinColumn(name = "bank_account_holder_name")
    private String bankAccountHolderName;
    @JoinColumn(name = "bank_name")
    private String bankName;
    @JoinColumn(name = "ifsc")
    private String ifsc;
    @JoinColumn(name = "bank_account_number")
    private String bankAccountNumber;
    @Basic
    @Column(name = "bio")
    @Type(type = "text")
    private String bio;
    @Basic
    @Column(name = "mobile")
    private String mobile;
    @ManyToOne
    @JoinColumn(name = "mobile_country_id", referencedColumnName = "id")
    private Country country;
    @Email
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "email_verified", columnDefinition = "boolean default false")
    private Boolean isEmailVerified;
    @Basic
    @Column(name = "mobile_verified", columnDefinition = "boolean default false")
    private Boolean isMobileVerified;
    @Basic
    @Column(name = "time_zone")
    private String timezone;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dob;
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "language_code")
    private Language preferLanguage;
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
    @ManyToOne
    @JoinColumn(name = "title_id", referencedColumnName = "id")
    private Title title;
    /*@ManyToMany()
    @JoinTable(name = "partner_address_user_mp", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<PartnerAddress> partnerAddresses;*/
    /*@ManyToMany()
    @JoinTable(name = "user_partner_relation_mp", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "partner_id"))
    @JsonIgnoreProperties("users")
    private Set<Partner> partners;*/
    /*@ManyToMany()
    @JoinTable(name = "appointment_m", joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "partner_id"))
    @JsonIgnoreProperties("users")
    private Set<Partner> partnersBooking;*/
    @ManyToOne
    @JoinColumn(name = "created_by_partner_id")
    private Partner createdByPartner;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdByUserId;
    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private Profession profession;
    /*@ManyToMany()
    @JoinTable(name = "user_certificate", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    private Set<Certificate> certificates;
    @ManyToMany()
    @JoinTable(name = "user_gallery_mp", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "gallery_id"))
    private Set<Gallery> galleries;*/
    @ManyToMany()
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
    @ManyToMany
    @JoinTable(name = "USER_ROLE_MP", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ManyToMany
    @JsonIgnoreProperties("partners")
    @JoinTable(name = "USER_DEPT_MP", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Set<Department> departments;
    @ManyToMany
    @JsonIgnoreProperties({"users", "createdByPartner"})
    @JoinTable(name = "user_pet_relation_mp", joinColumns = @JoinColumn(name = "from_user_id")
            , inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<Pet> pets;
    @Basic
    @CreatedDate
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic
    @LastModifiedDate
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modificationDate;
    @Basic
    @Column(name = "last_login")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastVisit;

    public User(Long id) {
        this.id = id;
    }

}
