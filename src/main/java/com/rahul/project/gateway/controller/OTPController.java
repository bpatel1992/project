package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.OtpDTO;
import com.rahul.project.gateway.dto.ReSendOtpDTO;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.dto.VerifyOtpDTO;
import com.rahul.project.gateway.service.OTPService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RESTController
public class OTPController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    OTPService OTPService;

    @ApiOperation(value = "Send OTP to user by random key and timezone", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/sendOtp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO sendOtp(@Valid @RequestBody OtpDTO otpDTO) throws Exception {
        return OTPService.sendOtp(otpDTO, new ResponseDTO());
    }

    @ApiOperation(value = "Resend OTP to user by random key and timezone", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/reSendOtp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO reSendOtp(@Valid @RequestBody ReSendOtpDTO reSendOtpDTO) throws Exception {
        return OTPService.reSendOtp(reSendOtpDTO, new ResponseDTO());
    }

    @ApiOperation(value = "verify OTP to user by random key and otp", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/verifyOtp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO verifyOtp(@Valid @RequestBody VerifyOtpDTO verifyOtpDTO) throws Exception {
        return OTPService.verifyOtp(verifyOtpDTO, new ResponseDTO());
    }

}
