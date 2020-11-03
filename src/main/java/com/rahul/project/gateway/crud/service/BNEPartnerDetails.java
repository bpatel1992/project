package com.rahul.project.gateway.crud.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dto.BusinessTimingDTO;
import com.rahul.project.gateway.dto.PartnerAddressDTO;
import com.rahul.project.gateway.dto.PartnerDTO;
import com.rahul.project.gateway.model.BusinessTiming;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.UserPartnerRelationMP;
import com.rahul.project.gateway.repository.PartnerAddressTimingRepository;
import com.rahul.project.gateway.repository.RelationMRepository;
import com.rahul.project.gateway.repository.UserPartnerRelationMPRepository;
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
@UIBeanSpecifier(id = "1", beanClass = BNEPartnerDetails.class)
@TransactionalService(value = "BNEPartnerDetails")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BNEPartnerDetails implements BNE {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserPartnerRelationMPRepository userPartnerRelationMPRepository;

    @Autowired
    private RelationMRepository relationMRepository;

    @Autowired
    private PartnerAddressTimingRepository partnerAddressTimingRepository;

    private Long id;

    public BNEPartnerDetails(Partner partner) {
        this.id = partner.getId();
    }

    @Override
    public List process(List list) {
        List<PartnerDTO> partnerDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Partner partner : (List<Partner>) list) {
                PartnerDTO partnerDTO = processData(partner);
                for (PartnerAddressDTO partnerAddress : partnerDTO.getPartnerAddresses()) {
                    Set<BusinessTiming> businessTimings = partnerAddressTimingRepository.businessTimings(partnerDTO.getId(), partnerAddress.getId());
                    Set<BusinessTimingDTO> businessTimingDTOS = businessTimings.stream().map(tim -> modelMapper.map(tim, BusinessTimingDTO.class))
                            .collect(Collectors.toSet());
                    partnerAddress.setBusinessTimings(businessTimingDTOS);
                }
                partnerDTOS.add(partnerDTO);
            }
        }
        return partnerDTOS;
    }

    @Override
    public Object process(Object o) {
        Partner partner = (Partner) o;
        UserPartnerRelationMP byUserAndAndPartner = userPartnerRelationMPRepository.getByUserAndAndPartner
                (partner.getUsers().iterator().next(), partner);
        byUserAndAndPartner.setRelation(relationMRepository.getByRelationName("Owner"));
        userPartnerRelationMPRepository.save(byUserAndAndPartner);
        return processData(partner);
    }

    @Override
    public Object updateProcess(Object o) {
        return processData((Partner) o);
    }

    private PartnerDTO processData(Partner partner) {
        PartnerDTO partnerDTO = modelMapper.map(partner, PartnerDTO.class);
        return partnerDTO;
    }
}
