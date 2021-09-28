package com.library.aritic.PushNotification;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.library.aritic.SharedPref.SharedPref;

import org.jetbrains.annotations.NotNull;

public class PushNotification {

    private String token = null;
    private tokenListener listener;


    private void handlePushNotification(Activity activity){

    }

    public PushNotification() {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
    }


    public void setTokenListener(tokenListener listener) {
        this.listener = listener;
    }
//    public void subscribeToTopic(String topic){
//        FirebaseMessaging.getInstance().subscribeToTopic(topic)
//                .addOnCompleteListener(task -> {
//                        if(task.isSuccessful()){
//                            System.out.println("Successfully subscribed to "+topic);
//                        }
//                        else {
//                            System.out.println("Cannot subscribe to "+topic);
//                        }
//                });
//    }
//
//    public void unsubscribeFromTopic(String topic){
//        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
//                        System.out.println("Successfully unsubscribed from "+topic);
//                    }
//                    else {
//                        System.out.println("Cannot unsubscribe from "+topic);
//                    }
//                });
//    }

    public void getFcmToken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Register: Generated ", task.getResult());
                        token = task.getResult();
                        SharedPref.updateOrInsertValue(SharedPref.DEVICE_TOKEN, token);
                        listener.onTokenSaved(token);
                    } else {
                        listener.onTokenFailure();
                    }
                });
        }
    public interface tokenListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onTokenSaved(String token);
        public void onTokenFailure();
        // or when data has been loaded

    }
}
