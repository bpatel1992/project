package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerAddressDTO {

    private Long id;
    private Long partnerId;
    private Boolean isPartner;
    //    private PartnerDTO partner;
    private String address;
    private String name;
    private Integer displayOrder;
    private CountryDTO country;
    private MasterDataDTO state;
    private MasterDataDTO city;
    private MasterDataDTO pinCode;
    private Double latitude;
    private Double longitude;
    private Set<PartnerContactNumberDTO> partnerContactNumbers;
    private Set<BusinessTimingDTO> businessTimings;
}

