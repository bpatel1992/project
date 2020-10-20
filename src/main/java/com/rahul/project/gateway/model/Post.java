package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @Basic
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    private String type;

    @Basic
    private String text;

    @Basic
    private String status;

    @Basic
    @Column(name = "url")
    private String url;

    @Temporal(TemporalType.DATE)
    @Column(name = "submission_date")
    private Date submissionDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "post_user_id", referencedColumnName = "user_id")
    private User postByUser;

    @ManyToMany()
    @JoinTable(name = "user_tagged_post", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> taggedUsers;

    @ManyToMany()
    @JoinTable(name = "pet_tagged_post", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<Pet> taggedPets;

    @ManyToMany()
    @JoinTable(name = "tag_post", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @Column(name = "views_count", columnDefinition = "BIGINT DEFAULT 0")
    private long viewsCount;

    @Column(name = "likes_count", columnDefinition = "BIGINT DEFAULT 0")
    private long likesCount;

    @Column(name = "comments_count", columnDefinition = "BIGINT DEFAULT 0")
    private long commentsCount;
}