package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.WeightUnit;
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
@UIBeanSpecifier(id = "1", beanClass = BNEWeightUnit.class)
@TransactionalService(value = "BNEWeightUnit")
public class BNEWeightUnit implements BNE {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (WeightUnit weightUnit : (List<WeightUnit>) list) {
                masterDataDTOS.add(modelMapper.map(weightUnit, MasterDataDTO.class));
            }
        }
        return masterDataDTOS;
    }

}
