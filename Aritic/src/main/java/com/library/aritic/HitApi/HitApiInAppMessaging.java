package com.library.aritic.HitApi;

import android.util.Log;

import com.library.aritic.ApiService.ApiService_InApp;
import com.library.aritic.ApiService.ApiService_InAppEvent;
import com.library.aritic.Data.Model.Request.InAppEventRequest;
import com.library.aritic.Data.Model.Response.InAppResponse.InAppEventResponse;
import com.library.aritic.Data.Model.Response.InAppResponse.InAppResponse;
import com.library.aritic.SharedPref.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HitApiInAppMessaging {
    private ApiService_InAppEvent apiService;

    public void hitApi(String objectId , String event) {
        setupRetrofit();
        sendRequestToApi(objectId, event);
    }

    private void setupRetrofit() {
        // TODO : another url will come here
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SharedPref.getValue("base_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService_InAppEvent.class);
    }

    private void sendRequestToApi(String objectId, String event) {
        InAppEventRequest inAppEventRequest = new InAppEventRequest(
                "inapp",
                                objectId,
                                event
        );
        log("Making API call for MessageId:" + objectId + ",for Event: " + event);
        Call<InAppEventResponse> call = apiService.getInAppEventResponse(inAppEventRequest);
        call.enqueue(new Callback<InAppEventResponse>() {
            @Override
            public void onResponse(Call<InAppEventResponse> call, Response<InAppEventResponse> response) {
               log("Response "  + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<InAppEventResponse> call, Throwable t) {
                log("Response Failure "  + t.getMessage());
            }
        });
    }

    public void log(String msg) {
        Log.w("InApp Page : ", msg);
    }
}
