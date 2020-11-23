package com.rahul.project.gateway.controller;


import com.rahul.project.gateway.dto.FeeDTO;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.service.TransactionProcessService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionProcessController {

//    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final
    TransactionProcessService transactionProcessService;

    public TransactionProcessController(TransactionProcessService transactionProcessService) {
        this.transactionProcessService = transactionProcessService;
    }


    @RequestMapping(path = {"/api/process/txn/save", "/oauth2/api/process/txn/save"},
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionProcessDTO processTxn(@Valid @RequestBody TransactionProcessDTO transactionProcessDTO) throws Exception {
        return transactionProcessService.processGatewayHostedSave(transactionProcessDTO);
    }


    @RequestMapping(path = {"/api/process/fee", "/oauth2/api/process/fee"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FeeDTO processFee(@Valid @RequestBody FeeDTO feeDto) throws Exception {
        return transactionProcessService.processFee(feeDto);
    }


}
