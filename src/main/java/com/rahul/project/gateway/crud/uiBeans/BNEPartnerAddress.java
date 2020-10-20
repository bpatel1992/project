package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.PartnerAddressDTO;
import com.rahul.project.gateway.model.PartnerAddress;
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
@UIBeanSpecifier(id = "1", beanClass = BNEPartnerAddress.class)
@TransactionalService(value = "BNEPartnerAddress")
public class BNEPartnerAddress implements BNE {

    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<PartnerAddressDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PartnerAddress partnerAddress : (List<PartnerAddress>) list) {
                PartnerAddressDTO masterDataDTO = modelMapper.map(partnerAddress, PartnerAddressDTO.class);
                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, PartnerAddressDTO.class);
    }
}
