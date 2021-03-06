package com.rahul.project.gateway.controller;


import com.rahul.project.gateway.dto.FeeDTO;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.service.TransactionProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Transaction service api, " + "\n" +
                "2. Fetch fee details",tags = { "Transaction process services" })
public class TransactionProcessController {

//    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final
    TransactionProcessService transactionProcessService;

    public TransactionProcessController(TransactionProcessService transactionProcessService) {
        this.transactionProcessService = transactionProcessService;
    }

    @ApiOperation(value = "Transaction service api", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/api/process/txn/save", "/oauth2/api/process/txn/save"},
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionProcessDTO processTxn(@Valid @RequestBody TransactionProcessDTO transactionProcessDTO) throws Exception {
        return transactionProcessService.processGatewayHostedSave(transactionProcessDTO);
    }

    @ApiOperation(value = "Fetch fee details", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = {"/api/process/fee", "/oauth2/api/process/fee"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FeeDTO processFee(@Valid @RequestBody FeeDTO feeDto) throws Exception {
        return transactionProcessService.processFee(feeDto);
    }


}
