package com.library.aritic.ApiService;

import com.library.aritic.Data.Model.Request.InAppRequest;
import com.library.aritic.Data.Model.Response.InAppResponse.InAppResponse;
import com.library.aritic.Data.Model.Response.InAppResponseNew.InappResponse2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService_InApp {

    @POST("ma/inapp/notification/dispatch")
    Call<InappResponse2>  getInAppResponse(@Body InAppRequest inAppRequest);
}
