package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDTO {

    private Long id;
    private Set<PartnerTypeDTO> partnerTypes;
    private String name;
    private Float partnerExperience;
    private String partnerDesc;
    private Set<PartnerAddressDTO> partnerAddresses;
    private CountryDTO country;
    private String mobile;
    private String email;
    private String address;
    private String fbLink;
    private String youtubeLink;
    private String instagramLink;
    private String twitterLink;
    private Boolean subscribed;
    private String userName;

}
