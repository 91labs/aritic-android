package com.example.ariticproject;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.library.aritic.Aritic;
import com.library.aritic.AriticLogger;
import com.library.aritic.PushNotification.AriticRemoteMessage;
import com.library.aritic.PushNotification.MyFirebaseMessagingService;

public class clientFirebaseReceiever implements Aritic.AriticPushHandler {



    public void log(String msg) {
        Log.d("CLIENT APP  : ", msg);
    }



    @Override
    public void remoteNotificationReceived(AriticRemoteMessage pushMessage) {
        Log.d("CLIENT APP Notification  : ", pushMessage.getRemoteMessage().getNotification().getTitle());
    }
}
