package com.library.aritic.HitApi;

import com.library.aritic.ApiService.ApiService_InApp;
import com.library.aritic.ApiService.ApiService_InAppEvent;
import com.library.aritic.Data.Model.Request.InAppEventRequest;
import com.library.aritic.Data.Model.Response.InAppResponse.InAppEventResponse;
import com.library.aritic.Data.Model.Response.InAppResponse.InAppResponse;

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
                .baseUrl("https://sprint.ctrmv.com/")
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

        Call<InAppEventResponse> call = apiService.getInAppEventResponse(inAppEventRequest);
        call.enqueue(new Callback<InAppEventResponse>() {
            @Override
            public void onResponse(Call<InAppEventResponse> call, Response<InAppEventResponse> response) {
                        System.out.println("InApp event : " + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<InAppEventResponse> call, Throwable t) {

            }
        });
    }
}
