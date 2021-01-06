package com.rahul.project.gateway.crud.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dto.BusinessTimingDTO;
import com.rahul.project.gateway.dto.PartnerAddressDTO;
import com.rahul.project.gateway.dto.UserDTO;
import com.rahul.project.gateway.model.BusinessTiming;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.UserAddressTimingRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEUserTimings.class)
@TransactionalService(value = "BNEUserTimings")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BNEUserTimings implements BNE {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserAddressTimingRepository userAddressTimingRepository;

    private Long id;

    public BNEUserTimings(Partner partner) {
        this.id = partner.getId();
    }

    @Override
    public List process(List list) {
        List<UserDTO> userDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (User user : (List<User>) list) {
                UserDTO userDTO = processData(user);
                processPartnerAddresses(userDTO.getPartnerAddresses(), userDTO.getId());
                userDTOS.add(userDTO);
            }
        }
        return userDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }

    private UserDTO processData(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public void processPartnerAddresses(Set<PartnerAddressDTO> partnerAddresses, Long id) {
        for (PartnerAddressDTO partnerAddress : partnerAddresses) {
            Set<BusinessTiming> businessTimings = userAddressTimingRepository.businessTimingsByUserIdAndPartnerAddressId(id, partnerAddress.getId());
            Set<BusinessTimingDTO> businessTimingDTOS = businessTimings.stream().map(tim -> modelMapper.map(tim, BusinessTimingDTO.class))
                    .collect(Collectors.toSet());
            partnerAddress.setBusinessTimings(businessTimingDTOS);
        }
    }
}
