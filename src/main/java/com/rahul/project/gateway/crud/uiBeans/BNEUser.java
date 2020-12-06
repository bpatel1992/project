package com.rahul.project.gateway.crud.uiBeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.CustomerProfileDTO;
import com.rahul.project.gateway.model.Authority;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.UserPartnerRelationMPRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@UIBeanSpecifier(id = "1", beanClass = BNEUser.class)
@TransactionalService(value = "BNEUser")
public class BNEUser implements BNE {
    private Long id;

    public BNEUser(User user) {
        this.id = user.getId();
    }

    @Autowired
    Environment environment;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BNEGender bneGender;
    @Autowired
    private BNELanguage bneLanguage;
    @Autowired
    private UserPartnerRelationMPRepository userPartnerRelationMPRepository;

    @Override
    public List process(List list) {
        List<CustomerProfileDTO> customerProfileDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (User user : (List<User>) list) {
                customerProfileDTOS.add(processObject(user));
            }
        }
        return customerProfileDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }


    public CustomerProfileDTO processObject(User user) {

        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
        customerProfileDTO.setId(user.getId());
//        customerProfileDTO.setRandomKey(user.getRandomKey());
        customerProfileDTO.setFirstName(user.getFirstName());
        customerProfileDTO.setLastName(user.getLastName());
        customerProfileDTO.setEmail(user.getEmail());
        customerProfileDTO.setTitleId(user.getTitle() != null ? user.getTitle().getId() : null);
        customerProfileDTO.setUserName(user.getUserName());
        customerProfileDTO.setProfileImage(user.getProfileImage());
        customerProfileDTO.setImageURL(user.getImageName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + user.getRandomKey() : null);
        customerProfileDTO.setAvatarURL(user.getAvatarName() != null ?
                environment.getRequiredProperty("gateway.api.url") +
                        "assets/user/avatar?randomKey=" + user.getRandomKey() : null);
        customerProfileDTO.setCoverURL(user.getCoverName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/cover?randomKey=" + user.getRandomKey() : null);
//        customerProfileDTO.setAuthorities(userAuthorityRepository.byUserId(user.getId()));
        customerProfileDTO.setAuthorities(user.getAuthorities());
        List<Authority> collect = user.getAuthorities().stream().filter(authority -> "ROLE_PARTNER".equalsIgnoreCase(authority.getName())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            Set<Partner> partners = userPartnerRelationMPRepository.byUserAndRelation(user.getId(), "Owner");
            if (CollectionUtils.isEmpty(partners))
                customerProfileDTO.setCanCreatePartner(true);
            else {
                customerProfileDTO.setPartnerId(partners.iterator().next().getId());
//                customerProfileDTO.setPartnerAddresses(user.getPartnerAddresses().stream().map(partnerAddress ->
//                        modelMapper.map(partnerAddress, PartnerAddressDTO.class)).collect(Collectors.toSet()));
//                customerProfileDTO.setPartners(partners.stream().map(partner -> modelMapper.map(partner, PartnerDTO.class))
//                        .collect(Collectors.toSet()));
            }
        }
        customerProfileDTO.setGenderId(user.getGender().getId());
        customerProfileDTO.setLanguageCode(user.getPreferLanguage() != null ? user.getPreferLanguage().getCode() : null);
        return customerProfileDTO;
    }
}
