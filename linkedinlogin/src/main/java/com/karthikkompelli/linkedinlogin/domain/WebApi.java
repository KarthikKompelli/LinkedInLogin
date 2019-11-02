package com.karthikkompelli.linkedinlogin.domain;


import com.karthikkompelli.linkedinlogin.data.AccessTokenResponse;
import com.karthikkompelli.linkedinlogin.data.UserEmailResponse;
import com.karthikkompelli.linkedinlogin.data.UserListResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static com.karthikkompelli.linkedinlogin.domain.ApiParam.AUTHORIZATION;
import static com.karthikkompelli.linkedinlogin.domain.ApiParam.CONTENT_TYPE;

public interface WebApi {
    @GET("me?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))")
    Call<UserListResponse> callUserDetailsApi(@Header(AUTHORIZATION) String authorization);

    @GET("emailAddress?q=members&projection=(elements*(handle~))")
    Call<UserEmailResponse> callUserEmailApi(@Header(AUTHORIZATION) String authorization);

    @POST("accessToken")
    Call<AccessTokenResponse> callAccessTokenApi(@Header(CONTENT_TYPE) String authorization, @QueryMap HashMap<String, String> hashMap);
}