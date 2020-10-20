package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dto.services.Messages;
import com.rahul.project.gateway.dto.services.NexmoSendSms;
import com.rahul.project.gateway.dto.services.NexmoSendSmsResponse;
import com.rahul.project.gateway.service.api.ApiServiceFactory;
import com.rahul.project.gateway.service.api.INexmo;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import retrofit2.Call;
import retrofit2.Response;

import java.lang.invoke.MethodHandles;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@TransactionalService
public class SmsService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    Environment environment;

    @Autowired
    Translator translator;

    @Autowired
    ApiServiceFactory apiServiceFactory;

    @Autowired
    CommonUtility commonUtility;

    public String sendSMS(String message, String toUser) throws Exception {
        if ("false".equalsIgnoreCase(environment.getRequiredProperty("sms.send.on")))
            return translator.toLocale("sms.success", new Object[]{toUser});

        NexmoSendSmsResponse nexmoSendSmsResponse;
        NexmoSendSms nexmoSendSms = new NexmoSendSms();
        nexmoSendSms.setApi_key(environment.getRequiredProperty("sms.api.key"));
        nexmoSendSms.setApi_secret(environment.getRequiredProperty("sms.api.secret"));
        nexmoSendSms.setFrom(environment.getRequiredProperty("sms.api.from.user"));
        nexmoSendSms.setText(message);
        nexmoSendSms.setTo(toUser);
        Call<NexmoSendSmsResponse> nexmoSendSmsResponseCall =
                apiServiceFactory.getRetrofit().create(INexmo.class).sendSMSNexmo(nexmoSendSms);
        Response<NexmoSendSmsResponse> nexmoSendSmsResponseResponse = nexmoSendSmsResponseCall.execute();
        if (nexmoSendSmsResponseResponse.isSuccessful()) {
            nexmoSendSmsResponse = nexmoSendSmsResponseResponse.body();
            if (nexmoSendSmsResponse != null && nexmoSendSmsResponse.getMessages().size() > 0) {
                for (Messages messages : nexmoSendSmsResponse.getMessages()) {
                    if (messages.getStatus() != 0) {
                        logger.error("message was not sent because of -->" + messages.getErrorText());
                        throw new BusinessException(translator.toLocale("sms.fail"));
                    }
                }
                return translator.toLocale("sms.success", new Object[]{commonUtility.maskMobile(toUser)});
            } else {
                throw new BusinessException(translator.toLocale("sms.fail"));
            }
        } else {
            throw new BusinessException(translator.toLocale("sms.fail"));
        }
    }
}
