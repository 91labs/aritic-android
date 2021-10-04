package com.example.ariticproject;

import android.util.Log;

import com.library.aritic.Aritic;
import com.library.aritic.PushNotification.AriticRemoteMessage;

public class clientFirebaseReceiever implements Aritic.AriticPushHandler {



    public void log(String msg) {
        Log.d("CLIENT APP  : ", msg);
    }



    @Override
    public void remoteNotificationReceived(AriticRemoteMessage pushMessage) {
//        Log.d("CLIENT APP Notification  : ", pushMessage.getRemoteMessage().getNotification().getTitle());
        pushMessage.complete(pushMessage);
    }
}
