package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.Profession;
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
@UIBeanSpecifier(id = "1", beanClass = BNEProfession.class)
@TransactionalService(value = "BNEProfession")
public class BNEProfession implements BNE {

    @Autowired
    Environment environment;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Profession gender : (List<Profession>) list) {
                masterDataDTOS.add(processEntity(gender));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Profession) o);
    }

    public MasterDataDTO processEntity(Profession gender) {
        if (gender == null)
            return null;
        else {
            MasterDataDTO genderDTO = new MasterDataDTO();
            genderDTO.setId(gender.getId());
            genderDTO.setName(gender.getLabel() != null ? gender.getLabel() : gender.getProfession());
            genderDTO.setImage(gender.getImageName() != null ?
                    environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + gender.getImageName() : null);
            return genderDTO;
        }
    }
}
