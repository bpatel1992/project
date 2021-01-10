package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.BusinessTimingDTO;
import com.rahul.project.gateway.dto.CustomerProfileDTO;
import com.rahul.project.gateway.dto.PartnerAddressDTO;
import com.rahul.project.gateway.model.PartnerAddress;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.model.UserAddressTiming;
import com.rahul.project.gateway.repository.PartnerAddressTimingRepository;
import com.rahul.project.gateway.repository.UserAddressTimingRepository;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNECustomerProfile.class)
@TransactionalService(value = "BNECustomerProfile")
public class BNECustomerProfile implements BNE {

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    Environment environment;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAddressTimingRepository userAddressTimingRepository;
    @Autowired
    PartnerAddressTimingRepository partnerAddressTimingRepository;
    @Autowired
    private ModelMapper modelMapper;

    private CustomerProfileDTO processData(User user) {
        CustomerProfileDTO profileDTO = modelMapper.map(user, CustomerProfileDTO.class);
        profileDTO.getCountry().setName(user.getCountry().getLabel() != null ? user.getCountry().getLabel() : user.getCountry().getName());
        profileDTO.getCountry().setImage(user.getCountry().getImageName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + user.getCountry().getImageName() : null);
        profileDTO.setImageURL(user.getImageName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + user.getRandomKey() : null);
        profileDTO.setAvatarURL(user.getAvatarName() != null ?
                environment.getRequiredProperty("gateway.api.url") +
                        "assets/user/avatar?randomKey=" + user.getRandomKey() : null);
        profileDTO.setCoverURL(user.getCoverName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/cover?randomKey=" + user.getRandomKey() : null);
       /* if (user.getCertificates() != null) {
            List<String> certificateURLs = new ArrayList<>();
            for (Certificate certificate : user.getCertificates()) {
                certificateURLs.add(environment.getRequiredProperty("gateway.api.url") + "assets/user/certificate?randomKey="
                        + user.getRandomKey() + "&fileName=" + certificate.getName());
            }
            profileDTO.setCertificateURLs(certificateURLs);
        }*/
        /*if (user.getGalleries() != null) {
            List<String> galleryURLs = null;
            if (user.getGalleries() != null && user.getGalleries().size() > 0) {
                galleryURLs = new ArrayList<>();
                for (Gallery gallery : user.getGalleries()) {
                    galleryURLs.add(environment.getRequiredProperty("gateway.api.url") + "assets/user/gallery?randomKey="
                            + user.getRandomKey() + "&fileName=" + gallery.getName());
                }
            }
            profileDTO.setGalleryURLs(galleryURLs);
        }*/
        profileDTO.setECardUrl(user.getUserName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "ecard/" + user.getUserName() : null);

/*
        if (profileDTO.getPartners() != null && profileDTO.getPartners().size() > 0) {
            for (PartnerDTO partnerDTO : profileDTO.getPartners()) {
                if (partnerDTO.getPartnerAddresses() != null && partnerDTO.getPartnerAddresses().size() > 0) {
                    for (PartnerAddressDTO partnerAddress : partnerDTO.getPartnerAddresses()) {
                        PartnerAddressTiming partnerAddressTiming =
                                partnerAddressTimingRepository.getByPartnerAndPartnerAddress
                                        (new Partner(partnerDTO.getId()), new PartnerAddress(partnerAddress.getId()));
                        if (partnerAddressTiming != null && partnerAddressTiming.getBusinessTimings() != null
                                && partnerAddressTiming.getBusinessTimings().size() > 0) {
                            Set<BusinessTimingDTO> businessTimingDTOS = partnerAddressTiming.getBusinessTimings()
                                    .stream().map(tim -> modelMapper.map(tim, BusinessTimingDTO.class))
                                    .collect(Collectors.toSet());
                            partnerAddress.setBusinessTimings(businessTimingDTOS);
                        }
                    }
                }
            }
        }
*/
        if (profileDTO.getPartnerAddresses() != null && profileDTO.getPartnerAddresses().size() > 0) {
            for (PartnerAddressDTO partnerAddress : profileDTO.getPartnerAddresses()) {
                UserAddressTiming userAddressTiming =
                        userAddressTimingRepository.getByUserAndPartnerAddress(user, new PartnerAddress(partnerAddress.getId()));
                if (userAddressTiming != null && userAddressTiming.getBusinessTimings() != null
                        && userAddressTiming.getBusinessTimings().size() > 0) {
                    Set<BusinessTimingDTO> businessTimingDTOS = userAddressTiming.getBusinessTimings()
                            .stream().map(tim -> modelMapper.map(tim, BusinessTimingDTO.class))
                            .collect(Collectors.toSet());
                    partnerAddress.setBusinessTimings(businessTimingDTOS);
                }
            }
        }
//        customerProfileDTO.setLastVisit(commonUtility.getDateFormatReport(user.getLastVisit()));
        return profileDTO;
    }

    @Override
    public List process(List list) {
        List<CustomerProfileDTO> customerProfileDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (User user : (List<User>) list) {
                customerProfileDTOS.add(processData(user));
            }
        }
        return customerProfileDTOS;
    }

    @Override
    public Object process(Object o) {
        User user = (User) o;
        user.setRandomKey(commonUtility.generateUserRandomKey());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        userRepository.save(user);
        return processData(user);
    }
}
