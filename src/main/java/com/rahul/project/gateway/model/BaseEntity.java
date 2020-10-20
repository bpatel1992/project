package com.rahul.project.gateway.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Entity Base Object. Every entity must extend this standard and they can
 * implement standard provided by this class.
 *
 * @author Rahul Malhotra
 */


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable, Comparable<Serializable> {

    /**
     * This is the default implementation so every concrete class does not enforced
     * to override compareTo
     */
    @Override
    public int compareTo(Serializable o) {
        return 0;
    }
}
