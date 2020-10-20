package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author rahul malhotra
 * @Date 2019-05-20
 */
@Data
@Entity
@Table(name = "post_comments_mapping")
public class PostCommentsMapping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User commentUser;

    @Basic
    private String comment;

    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Basic
    @Column(name = "modification_date")
    private Timestamp modificationDate;
}
