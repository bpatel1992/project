package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "SETTLEMENT_REQUEST")
public class SettlementRequest {

    @Id
    @SequenceGenerator(name = "SETTLEMENT_REQUEST_GEN", allocationSize = 1, sequenceName = "settlement_request_seq")
    @GeneratedValue(generator = "SETTLEMENT_REQUEST_GEN", strategy = GenerationType.SEQUENCE)
    @Column(name = "run_id")
    private Long settlementRunId;

    @Temporal(TemporalType.DATE)
    @Column(name = "cutoff_date")
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name = "cutoff_time")
    private Date time;

    @Column(name = "partner_code")
    private String partnerCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "log_time")
    private Date logTime;

    @Column(name = "run_count")
    private int runCount;

    @Column(name = "approved_time")
    private Timestamp approvedTime;

    @Column(name = "start_date")
    private Date startDate;
}
