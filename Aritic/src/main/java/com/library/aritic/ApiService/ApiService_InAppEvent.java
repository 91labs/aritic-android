package com.library.aritic.ApiService;

import com.library.aritic.Data.Model.Request.InAppEventRequest;
import com.library.aritic.Data.Model.Response.InAppResponse.InAppEventResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService_InAppEvent {
    @POST("ma/inapp/notification/tracking")
    Call<InAppEventResponse> getInAppEventResponse(@Body InAppEventRequest inAppEventRequest);
}
