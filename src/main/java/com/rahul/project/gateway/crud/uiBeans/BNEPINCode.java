package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.PINCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEPINCode.class)
@TransactionalService(value = "BNEPINCode")
public class BNEPINCode implements BNE {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PINCode pinCode : (List<PINCode>) list) {
                MasterDataDTO masterDataDTO = modelMapper.map(pinCode, MasterDataDTO.class);
//                countryDTO.setName(country.getLabel() != null ? country.getLabel() : country.getName());
//                masterDataDTO.setImage(pinCode.getImageName() != null ?
//                        environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + pinCode.getImageName() : null);
                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, MasterDataDTO.class);
    }
}
