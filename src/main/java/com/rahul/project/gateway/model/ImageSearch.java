package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Data
@Entity
@Table(name = "image_search")
public class ImageSearch implements Serializable {

    @Id
    @Basic
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Basic
    private boolean successful;

    @ManyToOne
    private File file;
}
