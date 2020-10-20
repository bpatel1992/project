package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.Title;
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
@UIBeanSpecifier(id = "1", beanClass = BNETitle.class)
@TransactionalService(value = "BNETitle")
public class BNETitle implements BNE {

    @Autowired
    Environment environment;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Title title : (List<Title>) list) {
                MasterDataDTO masterDataDTO = new MasterDataDTO();

                masterDataDTO.setId(title.getId());
                masterDataDTO.setName(title.getLabel() != null ? title.getLabel() : title.getTitle());
                masterDataDTO.setImage(title.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + title.getImageName() : null);

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
