package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.PartnerContactNumberDTO;
import com.rahul.project.gateway.model.PartnerContactNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEPartnerContactNumber.class)
@TransactionalService(value = "BNEPartnerContactNumber")
public class BNEPartnerContactNumber implements BNE {

    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<PartnerContactNumberDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PartnerContactNumber partnerContactNumber : (List<PartnerContactNumber>) list) {
                PartnerContactNumberDTO partnerContactNumberDTO = modelMapper.map(partnerContactNumber, PartnerContactNumberDTO.class);

//                partnerContactNumberDTO.setCountry(modelMapper.map(partnerContactNumber.getCountry(), MasterDataDTO.class));
//                partnerContactNumberDTO.setTitle(modelMapper.map(partnerContactNumber.getTitle(), MasterDataDTO.class));
//                partnerContactNumberDTO.getCountry().setName(partnerContactNumber.getCountry().getLabel() != null
//                        ? partnerContactNumber.getCountry().getLabel() : partnerContactNumber.getCountry().getName());
//                partnerContactNumberDTO.getTitle().setName(partnerContactNumber.getTitle().getLabel() != null
//                        ? partnerContactNumber.getTitle().getLabel() : partnerContactNumber.getTitle().getTitle());
//                partnerContactNumberDTO.setFirstName(partnerContactNumber.getFirstName());
//                partnerContactNumberDTO.setLastName(partnerContactNumber.getLastName());
//                partnerContactNumberDTO.setMobile(partnerContactNumber.getMobile());

                masterDataDTOS.add(partnerContactNumberDTO);
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, PartnerContactNumberDTO.class);
    }
}
