package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "partner_service_booking")
public class PartnerServiceBooking implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_address", referencedColumnName = "id")
    private PartnerAddress partnerAddress;

    @Column(name = "is_package")
    private boolean isPackage;

    @ManyToOne
    @JoinColumn(name = "service_package", referencedColumnName = "id")
    private ServicePackage servicePackage;
}
