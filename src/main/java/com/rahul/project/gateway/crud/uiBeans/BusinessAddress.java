package com.rahul.project.gateway.crud.uiBeans;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BusinessAddress {
    private Long id;
    private String title;
    private Integer displayOrder;
    private String businessAddress;//": "K-2, Brahmaputra Shopping Complex, Sector 29, Noida",
    private String businessAddressLink;
    private Set<BusinessTimingFlow> timings;
}
