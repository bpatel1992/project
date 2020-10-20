package com.rahul.project.gateway.service.api;

import com.rahul.project.gateway.dto.enablex.CreateRoomDTO;
import com.rahul.project.gateway.dto.enablex.create.room.response.CreateRoomResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2020-10-12
 */
public interface EnableXAPI {

    @POST("rooms")
    Call<CreateRoomResponse> createRoom(@Body CreateRoomDTO createRoomDTO);

}
