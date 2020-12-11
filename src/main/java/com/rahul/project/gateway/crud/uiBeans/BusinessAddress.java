package com.rahul.project.gateway.crud.uiBeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessAddress {
    private Long id;
    private String title;
    private Integer displayOrder;
    private String businessAddress;//": "K-2, Brahmaputra Shopping Complex, Sector 29, Noida",
    private Long addressTypeId;
    private String businessAddressLink;
    private Set<BusinessTimingFlow> timings;
}
