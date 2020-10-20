package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dto.ResponseHandlerDTO;
import com.rahul.project.gateway.dto.enablex.CreateRoomDTO;
import com.rahul.project.gateway.dto.enablex.create.room.response.CreateRoomResponse;
import com.rahul.project.gateway.dto.enablex.error.ErrorResponse;
import com.rahul.project.gateway.service.api.ApiServiceFactory;
import com.rahul.project.gateway.service.api.EnableXAPI;
import com.rahul.project.gateway.utility.Translator;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

import java.lang.annotation.Annotation;

@TransactionalService
public class EnableXService {
    @Autowired
    ApiServiceFactory apiServiceFactory;
    @Autowired
    Translator translator;

    public ResponseHandlerDTO<?> createRoom(CreateRoomDTO createRoomDTO) throws Exception {
        Call<CreateRoomResponse> createRoomResponseCall =
                apiServiceFactory.getRetrofitEnableX().create(EnableXAPI.class).createRoom(createRoomDTO);
        Response<CreateRoomResponse> response = createRoomResponseCall.execute();
        if (response.isSuccessful()) {
            ResponseHandlerDTO<CreateRoomResponse> responseHandlerDTO = new ResponseHandlerDTO<>();
            responseHandlerDTO.setT(response.body());
            return responseHandlerDTO;
        } else {
            ResponseHandlerDTO<ErrorResponse> responseHandlerDTO = new ResponseHandlerDTO<>();
            responseHandlerDTO.setT(parseError(response));
            responseHandlerDTO.setResponseCode("4001");
            responseHandlerDTO.setResponseMessage("Not able to create room, Try again later.");
            return responseHandlerDTO;
        }
    }

    public ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                apiServiceFactory.getRetrofitEnableX()
                        .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        ErrorResponse error;
        try {
            error = converter.convert(response.errorBody());
        } catch (Exception e) {
            return new ErrorResponse();
        }
        return error;
    }

}
