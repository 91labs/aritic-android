package com.library.aritic.ApiService;

import com.library.aritic.Data.Model.Request.User;
import com.library.aritic.Data.Model.Response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService_Registration {
    @POST("ma/inapp/notification/subscribe")
    Call<UserResponse> registerUser(@Body User user);
}

