package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.Language;
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
@UIBeanSpecifier(id = "1", beanClass = BNELanguage.class)
@TransactionalService(value = "BNELanguage")
public class BNELanguage implements BNE {

    @Autowired
    Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Language language : (List<Language>) list) {
                MasterDataDTO masterDataDTO = new MasterDataDTO();

                masterDataDTO.setLanguage(language.getCode());
                masterDataDTO.setName(language.getLabel() != null ? language.getLabel() : language.getName());
                masterDataDTO.setImage(language.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + language.getImageName() : null);

                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }

    public MasterDataDTO processEntity(Language language) {
        if (language == null)
            return null;
        else {
            MasterDataDTO masterDataDTO = new MasterDataDTO();
            masterDataDTO.setLanguage(language.getCode());
            masterDataDTO.setName(language.getLabel() != null ? language.getLabel() : language.getName());
            masterDataDTO.setImage(language.getImageName() != null ?
                    environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + language.getImageName() : null);
            return masterDataDTO;
        }
    }

    @Override
    public Object process(Object o) {
        return null;
    }
}
