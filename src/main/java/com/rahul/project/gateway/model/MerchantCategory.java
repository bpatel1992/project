package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "MERCHANTCATEGORYM_SEQ", allocationSize = 50, sequenceName = "MERCHANTCATEGORYM_SEQ")
@Table(name = "MERCHANT_CATEGORY_M")
public class MerchantCategory implements Serializable {

    @Id
    @GeneratedValue(generator = "MERCHANTCATEGORYM_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CATEGORY_DESC")
    private String categoryDescription;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "STATUS")
    private String status;

}
