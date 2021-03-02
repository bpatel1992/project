package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.service.TransactionProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;


@Controller
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Process transaction response, " + "\n" +
                "2. Updates email verification status of user", tags = {"Transaction response services"})
public class ResponseController {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    TransactionProcessService transactionProcessService;

    @Autowired
    private AbstractDao abstractDao;

    @ApiOperation(value = "This API processes transaction response", produces = MediaType.TEXT_HTML_VALUE)
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
            model.addAttribute("redirect", "yes");
        } else
            model.addAttribute("redirect", "no");
        logger.info("response status is " + response.getRespKey());
        return "pgResult";
    }

    @ApiOperation(value = "This API updates email verification status of user", produces = MediaType.TEXT_HTML_VALUE)
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
        return "verifyEmail";
    }

}
