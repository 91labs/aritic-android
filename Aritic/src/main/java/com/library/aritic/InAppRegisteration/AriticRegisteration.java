package com.library.aritic.InAppRegisteration;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.library.aritic.ApiService.ApiService_Registration;
import com.library.aritic.Data.Model.Request.User;
import com.library.aritic.Data.Model.Response.UserResponse;
import com.library.aritic.SharedPref.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AriticRegisteration {
        private ApiService_Registration apiService;
        private statusListener listener;


        public void registerUserWithDetails (
                @Nullable String email,
                @Nullable String userId,
                @Nullable String phone){
                       String deviceId = SharedPref.getValue(SharedPref.DEVICE_TOKEN);
                       updateOrInsertStorage(email,userId,phone,deviceId);
                       setupRetrofit();
                       hitApi(email,userId, deviceId, phone);
        }

    public void setListener(statusListener listener) {
        this.listener = listener;
    }

    // Note that developer must send all the fields
        // otherwise it will overwrite the details to null
        private void updateOrInsertStorage(
                                             String email,
                                             String userId,
                                             String phone,
                                             String deviceId) {
                        SharedPref.updateOrInsertValue(SharedPref.EMAIL, email);
                        SharedPref.updateOrInsertValue(SharedPref.USERID, userId);
                        SharedPref.updateOrInsertValue(SharedPref.PHONE, phone);
        }

        public void register() {
                setupRetrofit();
                String email = ""; String userId = ""; String phone = ""; String deviceId = "";
                email = SharedPref.getValue(SharedPref.EMAIL);
                userId = SharedPref.getValue(SharedPref.USERID);
                deviceId = SharedPref.getValue(SharedPref.DEVICE_TOKEN);
                phone = SharedPref.getValue(SharedPref.PHONE);

                hitApi(email, userId,deviceId, phone);
        }

        private void setupRetrofit() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SharedPref.getValue("base_url"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiService = retrofit.create(ApiService_Registration.class);
        }

        private void hitApi(@Nullable  String email,
                            @Nullable   String userId,
                            @NonNull String deviceId,
                            @Nullable String phone) {
                Log.d("RegisterAPI :DeviceId:   " , deviceId);
//            Log.d("RegisterAPI eamil :  " , email);
//            Log.d("RegisterAPI phone :  " , phone );
//            Log.d("RegisterAPI userId :  " , userId);
                User user = new User(email , userId , deviceId , phone);
                Call<UserResponse> call = apiService.registerUser(user);
                call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            // TODO: Response Parser
//                            if(response.body()) {
                                listener.onRegisterSuccessFull();
//                            }
//                            listener.onRegisterSuccessFull();;

                        }
                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.d("Register :  " , "Register Did not Happen");
                            // TODO: Error Code
                            listener.onRegisterFailure(t.getLocalizedMessage());
                        }
                });
        }


        public interface  statusListener {
            public void onRegisterSuccessFull();
            public void onRegisterFailure(String errorCode);
        }
}