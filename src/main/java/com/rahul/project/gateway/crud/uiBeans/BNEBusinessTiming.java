package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.BusinessTimingDTO;
import com.rahul.project.gateway.model.BusinessTiming;
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
@UIBeanSpecifier(id = "1", beanClass = BNEBusinessTiming.class)
@TransactionalService(value = "BNEBusinessTiming")
public class BNEBusinessTiming implements BNE {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<BusinessTimingDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (BusinessTiming businessTiming : (List<BusinessTiming>) list) {
                BusinessTimingDTO masterDataDTO = modelMapper.map(businessTiming, BusinessTimingDTO.class);
                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, BusinessTimingDTO.class);
    }
}
