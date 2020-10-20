package com.rahul.project.gateway.controller;


import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.service.TransactionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
public class TransactionProcessController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    TransactionProcessService transactionProcessService;


    @RequestMapping(path = {"/process/txn/save", "/oauth2/api/process/txn/save"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionProcessDTO processTxn(@Valid @RequestBody TransactionProcessDTO transactionProcessDTO) {

        logger.info("inside pos process in payment process controller");
        return transactionProcessService.processGatewayHostedSave(transactionProcessDTO);
    }

}
