package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEPetType.class)
@TransactionalService(value = "BNEPetType")
public class BNEPetType implements BNE {

    @Autowired
    Environment environment;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PetType petType : (List<PetType>) list) {
                MasterDataDTO masterDataDTO = new MasterDataDTO();

                masterDataDTO.setId(petType.getId());
                masterDataDTO.setName(petType.getLabel() != null ? petType.getLabel() : petType.getName());
                masterDataDTO.setImage(petType.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + petType.getImageName() : null);

                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }
}
