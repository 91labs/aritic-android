package com.library.aritic.HitApi;

import com.library.aritic.ApiService.ApiService_InAppEvent;
import com.library.aritic.ApiService.ApiService_PushEvent;
import com.library.aritic.Data.Model.Request.PushNotificationEventRequest;
import com.library.aritic.Data.Model.Response.PushNotificationEventResponse.PushNotificationEventResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HitApiPushNotification {
    private ApiService_PushEvent apiService;

    public void hitApi(int objectId, String event){
        setupRetrofit();
        sendRequestToApi(objectId, event);
    }

    private void setupRetrofit() {
        // TODO : another url will come here
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sprint.ctrmv.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService_PushEvent.class);
    }

    private void sendRequestToApi(int objectId, String event) {
        PushNotificationEventRequest pushNotificationEventRequest = new PushNotificationEventRequest(
                "push",
                                objectId,
                                event);

        Call<PushNotificationEventResponse> call = apiService.getPushEventResponse(pushNotificationEventRequest);
        call.enqueue(new Callback<PushNotificationEventResponse>() {
            @Override
            public void onResponse(Call<PushNotificationEventResponse> call, Response<PushNotificationEventResponse> response) {
                                    System.out.println(" Push Notification : " + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<PushNotificationEventResponse> call, Throwable t) {

            }
        });
    }

}
