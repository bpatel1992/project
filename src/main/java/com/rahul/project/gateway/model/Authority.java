package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class Authority implements Serializable {

//    @Id
//    @SequenceGenerator(name = "fee_m_gen", allocationSize = 1, sequenceName = "fee_m_seq")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_m_gen")
//    private long id;

    @Id
    @Column(name = "authority_id")
    private Long authorityId;

    @Basic
    @Column(name = "name")
    private String name;

    public Authority(Long authorityId) {
        this.authorityId = authorityId;
    }
}
