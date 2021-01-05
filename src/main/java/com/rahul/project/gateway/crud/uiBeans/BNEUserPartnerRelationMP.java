package com.rahul.project.gateway.crud.uiBeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.model.UserPartnerRelationMP;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEUserPartnerRelationMP.class)
@TransactionalService(value = "BNEUserPartnerRelationMP")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BNEUserPartnerRelationMP implements Serializable {

    private Long id;

    public BNEUserPartnerRelationMP(UserPartnerRelationMP relationMP) {
        this.id = relationMP.getUser().getId();
    }
}
