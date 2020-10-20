package com.rahul.project.gateway.service.api;

import com.rahul.project.gateway.dto.TokenInfoResponseDTO;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2020-09-27
 */
public interface GoogleApi {

    @POST("tokeninfo")
    @Headers({"Content-Type: application/json"})
    Call<TokenInfoResponseDTO> authenticate(@Query("access_token") String accessToken);

    @POST("me")
    @Headers({"Content-Type: application/json"})
    Call<TokenInfoResponseDTO> authenticateFacebook(@Query("access_token") String accessToken,
                                                    @Query("fields") String fields);
}
