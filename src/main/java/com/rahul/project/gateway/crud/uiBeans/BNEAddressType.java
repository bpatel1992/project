package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.AddressType;
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
@UIBeanSpecifier(id = "1", beanClass = BNEAddressType.class)
@TransactionalService(value = "BNEAddressType")
public class BNEAddressType implements BNE {

    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AddressType AddressType : (List<AddressType>) list) {
                MasterDataDTO masterDataDTO = modelMapper.map(AddressType, MasterDataDTO.class);
//                countryDTO.setName(country.getLabel() != null ? country.getLabel() : country.getName());
                masterDataDTO.setImage(AddressType.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + AddressType.getImageName() : null);
                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }
}
