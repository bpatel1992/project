package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.model.User;
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
public class ResponseController {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    TransactionProcessService transactionProcessService;

    @Autowired
    private AbstractDao abstractDao;


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

        transactionProcessDTO.setAttributes(attributes);
        transactionProcessDTO.setTransactionGatewayReferenceId(request.getParameter("gatewayTxnRef"));
        transactionProcessDTO.setGatewayName(request.getParameter("gatewayName"));
        transactionProcessDTO.setBankName(request.getParameter("bankName"));
        transactionProcessDTO.setTransactionId(request.getParameter("systemTxnRef"));
        transactionProcessDTO.setStatus(request.getParameter("txnStatus"));
        TransactionProcessDTO response = transactionProcessService.processGatewayResponse(transactionProcessDTO);
        if (response.getRedirectURL() != null) {
            model.addAttribute("url", response.getRedirectURL());
            model.addAttribute("smsInvoice", "notEnabled");
        } else
            model.addAttribute("smsInvoice", "enabled");
        logger.info("response status is " + response.getRespKey());
        return "pgResult";
    }


    @RequestMapping("update/email-status")
    public String updateEmailStatus(HttpServletRequest request, Model model) throws Exception {
        logger.info("in getProcessTxnResponse");
        String message = "";
        String result = "";
        String userId = request.getParameter("id");
        if (userId != null) {
            User user = abstractDao.getEntityById(User.class, userId);
            if (user != null) {
                Boolean b = user.getIsEmailVerified();
                if (b) {
                    message = "Your Email is Already Verified";
                } else {
                    user.setIsEmailVerified(true);
                    abstractDao.saveOrUpdateEntity(user);

                    message = "Your Email is Successfully Verified";
                }
            } else {
                message = "Account Not Found Please Contact to Admin or SignUp Again";
            }
        } else {
            message = "Account Not Found Please Contact to Admin or SignUp Again";
        }

        model.addAttribute("message", message);

        //        TransactionProcessDTO transactionProcessDTO = new TransactionProcessDTO();
//        Map<String, String[]> map = request.getParameterMap();
//        HashMap<String, Object> attributes = new HashMap<>();
//        for (String str : map.keySet()) {
//            result = (result.equalsIgnoreCase("") ? result : result + ",") + str + "=" + request.getParameter(str);
//            attributes.put(str, request.getParameter(str));
//            logger.info(" parameter --> " + str + " param value -->" + request.getParameter(str));
//        }
//        model.addAttribute("result", result);
//
//        transactionProcessDTO.setTransactionGatewayReferenceId(request.getParameter("gatewayTxnRef"));
//        transactionProcessDTO.setGatewayName(request.getParameter("gatewayName"));
//        transactionProcessDTO.setBankName(request.getParameter("bankName"));
//        transactionProcessDTO.setTransactionId(request.getParameter("systemTxnRef"));
//        transactionProcessDTO.setStatus(request.getParameter("txnStatus"));
//
//        TransactionProcessDTO response = new TransactionProcessDTO();
////        TransactionProcessDTO response = transactionProcessService.processGatewayHostedUpdate(transactionProcessDTO);
//        response.setRedirectURL(null);
//        if (response.getRedirectURL() != null) {
//            model.addAttribute("url", response.getRedirectURL());
//            model.addAttribute("smsInvoice", "enabled");
//        } else
//            model.addAttribute("smsInvoice", "notEnabled");
//        logger.info("response status is " + response.getRespKey());

        return "verifyEmail";
    }

}
