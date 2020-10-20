package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.DayDTO;
import com.rahul.project.gateway.model.Day;
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
@UIBeanSpecifier(id = "1", beanClass = BNEDay.class)
@TransactionalService(value = "BNEDay")
public class BNEDay implements BNE {

    @Autowired
    Environment environment;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<DayDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Day day : (List<Day>) list) {
                masterDataDTOS.add(processEntity(day));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Day) o);
    }

    private DayDTO processEntity(Day day) {
        DayDTO dayDTO = modelMapper.map(day, DayDTO.class);
        dayDTO.setName(day.getLabel() != null ? day.getLabel() : day.getName());
        dayDTO.setShortCode(day.getShortCodeLabel() != null ? day.getShortCodeLabel() : day.getShortCode());
        return dayDTO;
    }
}
