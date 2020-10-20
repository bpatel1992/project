package com.rahul.project.gateway.service.api;

import com.rahul.project.gateway.dto.services.NexmoSendSms;
import com.rahul.project.gateway.dto.services.NexmoSendSmsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public interface INexmo {

    @POST("sms/json")
    Call<NexmoSendSmsResponse> sendSMSNexmo(@Body NexmoSendSms nexmoSendSms);

}
