package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "business_timing")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessTiming implements Serializable {
    @Id
    @SequenceGenerator(name = "business_timing_gen", allocationSize = 1, sequenceName = "business_timing_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_timing_gen")
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "business_timing_time_range_mp", joinColumns = @JoinColumn(name = "business_timing_id"),
            inverseJoinColumns = @JoinColumn(name = "time_range_id"))
    private Set<TimeRange> timeRange;


    @ManyToMany
    @JoinTable(name = "business_timing_day_mp", joinColumns = @JoinColumn(name = "business_timing_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id"))
    private Set<Day> days;

    public BusinessTiming(Long id) {
        this.id = id;
    }
}
