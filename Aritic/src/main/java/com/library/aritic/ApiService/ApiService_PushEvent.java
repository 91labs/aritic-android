package com.library.aritic.ApiService;
import com.library.aritic.Data.Model.Request.PushNotificationEventRequest;
import com.library.aritic.Data.Model.Response.PushNotificationEventResponse.PushNotificationEventResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService_PushEvent {
    @POST("ma/inapp/notification/tracking")
    Call<PushNotificationEventResponse> getPushEventResponse(
            @Body PushNotificationEventRequest pushNotificationEventRequest
    );

}
