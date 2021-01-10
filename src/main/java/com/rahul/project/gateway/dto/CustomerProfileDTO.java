
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.model.Authority;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerProfileDTO {
    private Long id;
    private Set<Authority> authorities;
    private String bio;
    private String userName;
    private Integer userExperience;
    private BigDecimal userCharges;
    private Integer chargesSlotInMin;
    private String avatarURL;
    private String imageURL;
    private String coverURL;
    private String profileImage;
    private List<String> certificateURLs;
    private String randomKey;
    private String firstName;
    private String lastName;
    private String mobile;
    private MasterDataDTO country;
    private String email;
    private Boolean isEmailVerified;
    private Boolean isMobileVerified;
    private Date dob;
    private GenderDTO gender;
    private Long genderId;
    private String languageCode;
    private MasterDataDTO language;
    private MasterDataDTO title;
    private Long titleId;
    //    private Set<PartnerDTO> partners;
    private Long partnerId;
    private MasterDataDTO profession;
    private Set<PartnerAddressDTO> partnerAddresses;
    private String eCardUrl;
    private Boolean canCreatePartner;
    private Boolean subscribed;

    private String bankAccountHolderName;
    private String bankName;
    private String ifsc;
    private String bankAccountNumber;

    private String registrationNumber;
    private String registrationCouncil;
    private String registrationYear;
    private String degree;
    private String awardRecognition;

    private String fbLink;
    private String youtubeLink;
    private String instagramLink;
    private String twitterLink;
}
