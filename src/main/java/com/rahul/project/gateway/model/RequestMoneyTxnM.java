package com.rahul.project.gateway.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "REQUESTMONEY_SEQ", allocationSize = 50, sequenceName = "REQUESTMONEY_SEQ")
@Table(name = "REQUEST_MONEY_TXN_M")
public class RequestMoneyTxnM implements Serializable {


    @Id
    @GeneratedValue(generator = "REQUESTMONEY_SEQ", strategy = GenerationType.AUTO)
    @Column(name = "id")
    long id;
    @Column(name = "system_txn_id")
    String systemTxnId;
    @Column(name = "amount")
    BigDecimal amount;
    @Column(name = "message")
    String message;
    @Column(name = "from_user_name")
    String fromUserName;
    @Column(name = "to_user_name")
    String toUserName;
    @Column(name = "request_type")
    String requestType;
    @Column(name = "request_status")
    String requestStatus;
    @Column(name = "time_stamp")
    Timestamp timestamp;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "txn_id", referencedColumnName = "id")
    private Transaction txnId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_user_id", referencedColumnName = "USER_ID")
    private User fromUserId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_user_id", referencedColumnName = "USER_ID")
    private User toUserId;

}
