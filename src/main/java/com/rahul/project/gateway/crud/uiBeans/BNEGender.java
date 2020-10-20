package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.GenderDTO;
import com.rahul.project.gateway.model.Gender;
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
@UIBeanSpecifier(id = "1", beanClass = BNEGender.class)
@TransactionalService(value = "BNEGender")
public class BNEGender implements BNE {

    @Autowired
    Environment environment;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<GenderDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Gender gender : (List<Gender>) list) {
                masterDataDTOS.add(processEntity(gender));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Gender) o);
    }

    public GenderDTO processEntity(Gender gender) {
        if (gender == null)
            return null;
        else {
            GenderDTO genderDTO = new GenderDTO();
            genderDTO.setId(gender.getId());
            genderDTO.setName(gender.getLabel() != null ? gender.getLabel() : gender.getName());
            genderDTO.setImage(gender.getImageName() != null ?
                    environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + gender.getImageName() : null);
            return genderDTO;
        }
    }
}
