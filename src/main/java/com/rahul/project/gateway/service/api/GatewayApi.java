package com.rahul.project.gateway.service.api;

import com.rahul.project.gateway.dto.services.OauthResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public interface GatewayApi {

    @POST("oauth/token")
    @FormUrlEncoded
    @Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
    Call<OauthResponse> authenticate(@Header("Authorization") String basicToken, @Field("username") String username
            , @Field("password") String password, @Field("grant_type") String grantType, @Field("random_key") String randomKey);

    @POST("oauth/token")
    @FormUrlEncoded
    @Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
    Call<OauthResponse> refreshToken(@Field("refresh_token") String accessToken, @Field("grant_type") String grantType);

}
