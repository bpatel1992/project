package com.rahul.project.gateway.crud.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
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

    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<TransactionProcessDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Transaction transaction : (List<Transaction>) list) {
                masterDataDTOS.add(processEntity(transaction));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Transaction) o);
    }

    private TransactionProcessDTO processEntity(Transaction transaction) {
        TransactionProcessDTO transactionProcessDTO = modelMapper.map(transaction, TransactionProcessDTO.class);
        return transactionProcessDTO;
    }

    @Autowired
    public BNECustomerTxn(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
//        modelMapper.addMappings(transactionMapping);
//        modelMapper.addMappings(transactionFieldMapping);
    }


    /*PropertyMap<TransactionProcessDTO, Transaction> transactionMapping = new PropertyMap<TransactionProcessDTO, Transaction>() {
        protected void configure() {
            map().getServices().setId(source.getServiceId());
        }
    };
    PropertyMap<Transaction, TransactionProcessDTO> transactionFieldMapping = new PropertyMap<Transaction, TransactionProcessDTO>() {
        protected void configure() {
            map().setServiceId(source.getServices().getId());

        }
    };*/

}
