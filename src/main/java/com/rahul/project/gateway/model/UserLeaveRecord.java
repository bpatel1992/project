package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_leave_record")
public class UserLeaveRecord {


    @Id
    @SequenceGenerator(name = "leave_record_gen", allocationSize = 1, sequenceName = "leave_record_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_record_gen")
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "leave_timing_time_range_mp", joinColumns = @JoinColumn(name = "user_leave_id"),
            inverseJoinColumns = @JoinColumn(name = "time_range_id"))
    private Set<TimeRange> timeRange;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "from_date")
    private Date fromDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "availability", columnDefinition = "boolean default false")
    private boolean availability;


    @ManyToOne
    @MapsId("id1")
    @JoinColumn(name = "address_id")
    private PartnerAddress partnerAddress;

    public UserLeaveRecord(Long id) {
        this.id = id;
    }
}
