package com.library.aritic.PushNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.library.aritic.HitApi.HitApiPushNotification;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int push_id = intent.getIntExtra("push_id", 0);
//        intent.getDataString()
        Log.d("DS", intent.getDataString());
        Log.d("ACTION", intent.getAction());
        String isCancelled = intent.getAction();
        if(isCancelled.equals("push_cancelled")){
            handlePushCancelled(push_id, context);
        }
        else{
            handlePushClicked(push_id, context);
        }

    }

    private void handlePushClicked(int push_id, Context context) {
        System.out.println("Aritic library : notification clicked");
        HitApiPushNotification hitApiPushNotification = new HitApiPushNotification();
        hitApiPushNotification.hitApi(push_id, "clicked");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(0);
    }

    private void handlePushCancelled(int push_id, Context context) {
        System.out.println("Aritic library : notification cancelled");
        HitApiPushNotification hitApiPushNotification = new HitApiPushNotification();
        hitApiPushNotification.hitApi(push_id, "cancelled");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(0);
    }
}
