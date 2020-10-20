package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class CompositeId implements Serializable {

    private Long id1;
    private Long id2;

    public CompositeId(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

}
