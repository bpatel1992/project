package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.service.TransactionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;


@Controller
public class TransactionResponseController {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    TransactionProcessService transactionProcessService;


    @RequestMapping("/process/txn/response")
    public String getProcessTxnResponse(HttpServletRequest request, Model model) throws Exception {
        logger.info("in getProcessTxnResponse");
        String result = "";
        TransactionProcessDTO transactionProcessDTO = new TransactionProcessDTO();
         Map<String, String[]> map = request.getParameterMap();
         HashMap<String, Object> attributes = new HashMap<>();
                for (String str : map.keySet()) {
            result = (result.equalsIgnoreCase("") ? result : result + ",") + str + "=" + request.getParameter(str);
            attributes.put(str, request.getParameter(str));
            logger.info(" parameter --> " + str + " param value -->" + request.getParameter(str));
        }
        model.addAttribute("result", result);

        transactionProcessDTO.setTransactionGatewayReferenceId(request.getParameter("gatewayTxnRef"));
        transactionProcessDTO.setGatewayName(request.getParameter("gatewayName"));
        transactionProcessDTO.setBankName(request.getParameter("bankName"));
        transactionProcessDTO.setTransactionId(request.getParameter("systemTxnRef"));
        transactionProcessDTO.setStatus(request.getParameter("txnStatus"));


        TransactionProcessDTO response = transactionProcessService.processGatewayHostedUpdate(transactionProcessDTO);
        if (response.getRedirectURL() != null) {
            model.addAttribute("url", response.getRedirectURL());
             model.addAttribute("smsInvoice","enabled");
        }
        else
            model.addAttribute("smsInvoice","notEnabled");
        logger.info("response status is " + response.getRespKey());
        return "pgResult";
    }

}
