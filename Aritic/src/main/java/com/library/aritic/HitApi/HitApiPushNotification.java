package com.library.aritic.HitApi;

import android.util.Log;

import com.library.aritic.ApiService.ApiService_InAppEvent;
import com.library.aritic.ApiService.ApiService_PushEvent;
import com.library.aritic.Data.Model.Request.PushNotificationEventRequest;
import com.library.aritic.Data.Model.Response.PushNotificationEventResponse.PushNotificationEventResponse;
import com.library.aritic.SharedPref.SharedPref;

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

    public void hitButtonClickApi(int objectId, String event, int buttonId){
        setupRetrofit();
        sendButtonRequestToApi(objectId, event, buttonId);
    }

    private void setupRetrofit() {
        // TODO : another url will come here
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SharedPref.getValue("base_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService_PushEvent.class);
    }

    private void sendRequestToApi(int objectId, String event) {
        PushNotificationEventRequest pushNotificationEventRequest = new PushNotificationEventRequest(
                "push",
                                objectId,
                                event);
        log("Push Message API  hitting for: " +objectId + " for the event: " + event);
        Call<PushNotificationEventResponse> call = apiService.getPushEventResponse(pushNotificationEventRequest);
        call.enqueue(new Callback<PushNotificationEventResponse>() {
            @Override
            public void onResponse(Call<PushNotificationEventResponse> call, Response<PushNotificationEventResponse> response) {
                                    System.out.println(" Push Notification : " + response.body().getMessage());
                                    log("Respinsesuccess" + response.message());
            }

            @Override
            public void onFailure(Call<PushNotificationEventResponse> call, Throwable t) {
                log("Respinsesuccess" + t.getMessage());
            }
        });
    }



    private void sendButtonRequestToApi(int objectId, String event, int buttonId) {
        PushNotificationEventRequest pushNotificationEventRequest = new PushNotificationEventRequest(
                "push",
                objectId,
                event, buttonId);
        log("Push Message API  hitting for: " +objectId + " for the event: " + event);
        Call<PushNotificationEventResponse> call = apiService.getPushEventResponse(pushNotificationEventRequest);
        call.enqueue(new Callback<PushNotificationEventResponse>() {
            @Override
            public void onResponse(Call<PushNotificationEventResponse> call, Response<PushNotificationEventResponse> response) {
                System.out.println(" Push Notification : " + response.body().getMessage());
                log("Respinsesuccess" + response.message());
            }

            @Override
            public void onFailure(Call<PushNotificationEventResponse> call, Throwable t) {
                log("Respinsesuccess" + t.getMessage());
            }
        });
    }

    public void log(String msg) {
        Log.d("PUSHAPI : ", msg);
    }

}
