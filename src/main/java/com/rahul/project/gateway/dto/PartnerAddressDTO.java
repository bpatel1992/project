package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
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
    private BigDecimal userCharges;
    private Integer chargesSlotInMin;
    //    private PartnerDTO partner;
    private String address;
    private MasterDataDTO addressType;
    private String name;
    private Integer displayOrder;
    private CountryDTO country;
    private MasterDataDTO state;
    private MasterDataDTO city;
    private MasterDataDTO pinCode;
    private Double latitude;
    private Double longitude;
    private Set<PartnerContactNumberDTO> partnerContactNumbers;
    private Boolean status;
    private Set<BusinessTimingDTO> businessTimings;
}

