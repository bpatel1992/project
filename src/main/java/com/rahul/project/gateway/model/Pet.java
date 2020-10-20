package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * @author rahul malhotra
 * @Date 2019-04-21
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "pet_m")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pet extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "user_name", unique = true)
    private String userName;

    @ManyToOne
    @JoinColumn(name = "pt_id", referencedColumnName = "id")
    private PetType petType;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "image_name", length = 100)
    private String imageName;

    @Basic
    @Column(name = "random_key")
    private String randomKey;

    @ManyToOne
    @JoinColumn(name = "weight_id", referencedColumnName = "id")
    private WeightUnit weightUnit;

    @Basic
    @Column(name = "weight_value")
    private Double weightValue;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "created_by_partner_id")
    private Partner createdByPartner;

    @ManyToOne
    @JoinColumn(name = "pb_id", referencedColumnName = "id")
    private PetBreed petBreed;
    @Basic
    @Column(name = "dob")
    @Temporal(value = TemporalType.DATE)
    private Date dob;
    //it is in weeks
    @Basic
    @Column(name = "year_old")
    private Integer yearOld;
    @Basic
    @Column(name = "year_old_entry_date")
    private Timestamp yearOldEntryDate;
    @JoinTable(
            name = "user_pet_relation_mp",
            joinColumns = @JoinColumn(
                    name = "pet_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "from_user_id",
                    referencedColumnName = "user_id"
            )
    )
    @ManyToMany
    @JsonIgnoreProperties("pets")
    private Set<User> users;
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

    public Pet(Long id) {
        this.id = id;
    }
}
