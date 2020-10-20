package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "time_range")
public class TimeRange implements Serializable {
    @Id
    @SequenceGenerator(name = "time_range_gen", initialValue = 10, allocationSize = 1, sequenceName = "time_range_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_range_gen")
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String timeRange;
    @Basic
    @Column(name = "from_time")
    private Integer fromHours;
    @Basic
    @Column(name = "from_minutes")
    private Integer fromMinutes;

    //0 to 23
    @Basic
    @Column(name = "to_hours")
    private Integer toHours;
    //0 to 59
    @Basic
    @Column(name = "to_minutes")
    private Integer toMinutes;
    @Basic
    @Column(name = "display_order")
    private Long displayOrder;

    public TimeRange(Long id) {
        this.id = id;
    }
}
