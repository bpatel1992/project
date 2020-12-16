package com.rahul.project.gateway.model;
// Generated Jan 8, 2010 6:42:14 PM by Hibernate Tools 3.2.2.GA

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "NET_POSITION")
public class NetPosition implements Serializable {

    private static final long serialVersionUID = -9121089342682790704L;

    @Id
    @SequenceGenerator(name = "NETPOSITION_SEQ", allocationSize = 1, sequenceName = "NETPOSITION_SEQ")
    @GeneratedValue(generator = "NETPOSITION_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "NET_POSITION_ID")
    private Long netPositionId;

    @Column(name = "PARTNER_CODE")
    private long partnerCode;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVAL_DATE")
    private Date approvalDate;

    @Column(name = "batch_urn")
    private String uniqueRefNum;

    @Column(name = "TOTAL_TXN")
    private long totalTxn;

    @ManyToOne
    @JoinColumn(name = "run_id")
    private SettlementRequest settlementRequest;

    @Column(name = "currency")
    private String currency;

    @ManyToMany
    @JoinTable(name = "txn_transfer_net_position", joinColumns = @JoinColumn(name = "NET_POSITION_ID", referencedColumnName = "NET_POSITION_ID")
            , inverseJoinColumns = @JoinColumn(name = "transfer_id", referencedColumnName = "id"))
    private List<Transfer> transfers;


}
