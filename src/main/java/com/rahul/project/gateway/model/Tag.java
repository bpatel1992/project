package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "tag")
public class Tag implements Serializable {
    @Id
    @Column(name = "tag_id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @ManyToMany()
    @JoinTable(name = "tag_post", joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Post> posts;
}