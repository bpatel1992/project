package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author rahul malhotra
 * date 2020-10-03
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "news_letter_subscription_m")
public class NewsLetterSubscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "subscribed", columnDefinition = "boolean default false", nullable = false)
    private boolean subscribed;
    @Email
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;
    @Basic
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modificationDate;

    public NewsLetterSubscription(Long id) {
        this.id = id;
    }
}
