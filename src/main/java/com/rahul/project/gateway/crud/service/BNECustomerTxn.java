package com.rahul.project.gateway.crud.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dto.DayDTO;
import com.rahul.project.gateway.model.Transaction;
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
@UIBeanSpecifier(id = "1", beanClass = BNECustomerTxn.class)
@TransactionalService(value = "BNECustomerTxn")
public class BNECustomerTxn implements BNE {

    @Autowired
    Environment environment;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<DayDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Transaction day : (List<Transaction>) list) {
                masterDataDTOS.add(processEntity(day));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Transaction) o);
    }

    private DayDTO processEntity(Transaction day) {
        DayDTO dayDTO = new DayDTO();
        dayDTO.setId(day.getId());
        return dayDTO;
    }
}
