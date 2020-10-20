package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "PAYMENT_MODE_M")
public class PaymentMode implements Serializable {

    private static final long serialVersionUID = 6917453377829955286L;
    @Id
    @SequenceGenerator(name = "PAYMENTMODEM_GEN", allocationSize = 1, sequenceName = "PAYMENTMODEM_SEQ")
    @GeneratedValue(generator = "PAYMENTMODEM_GEN", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MODE_CODE")
    private String modeCode;
    @Column(name = "MODE_DESC")
    private String modeDesc;

    public PaymentMode() {
    }

    public PaymentMode(Long id) {
        this.id = id;
    }
}

