package com.rahul.project.gateway.crud.uiBeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.model.Partner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEPartnerPayout.class)
@TransactionalService(value = "BNEPartnerPayout")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BNEPartnerPayout implements Serializable {

    private Long id;
    private String bankName;
    private String ifsc;
    private String bankAccountNumber;

    public BNEPartnerPayout(Partner partner) {
        this.id = partner.getId();
        this.bankName = partner.getBankName();
        this.ifsc = partner.getIfsc();
        this.bankAccountNumber = partner.getBankAccountNumber();
    }

}
