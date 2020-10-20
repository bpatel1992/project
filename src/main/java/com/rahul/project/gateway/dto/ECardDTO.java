
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.crud.uiBeans.BusinessAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ECardDTO {
    private String name;//": "Dr. Rishi Sood",
    private String bio;
    private BigDecimal userCharges;
    private Integer userExperience;
    private String avatarURL;
    private String imageURL;
    private String coverURL;
    private String businessName;//": "Pet & Vet Clinic",
    private List<BusinessAddress> businessAddress;
    private String phone;//": "7827836303",
    private String address;//": "K-2, Brahmaputra Shopping Complex, Sector 29, Noida",
    private String addressLink;
    private String whatsAppPhone;//": "+91-9711405054",
    private String email;//": "rishi14sood@yahoo.co.in",
    private String profession;//": "Veterinarian",
    private String fbLink;//": "",
    private String youtubeLink;//": "",
    private String instagramLink;//": "",
    private String twitterLink;//": "",
    private String whatsAppLink;//": "",
    private String whatsShareLink;//": "",
    private String emailLink;//": "",
    private String phoneLink;//": "",
}
