package com.library.aritic.PushNotification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.library.aritic.InAppRegisteration.AriticRegisteration;
import com.library.aritic.R;
import com.library.aritic.SharedPref.SharedPref;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String channelId = "channel FCM";
    private String testTitle;
    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
        Log.d("REGISTER: onNewToken " ,"A new token");
//        registerNewToken(s);
    }

    private void registerNewToken(String token) {
        System.out.println("new Token is " + token);
        SharedPref.updateOrInsertValue(SharedPref.DEVICE_TOKEN, token);
        AriticRegisteration ariticRegisteration = new AriticRegisteration();
//        ariticRegisteration.registerNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("PUSH", "message Received");
        if(!remoteMessage.getData().isEmpty())
            testTitle = remoteMessage.getData().get("title");
            System.out.println("datapayload : " + testTitle);
            sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
//        int push_id = Integer.parseInt(
//                Objects.requireNonNull(remoteMessage.getData().get("push_id")));
        int push_id  = 1231123;
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("push_id",push_id );

        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_coffee_24)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setContentIntent(actionIntent)
                .setDeleteIntent(getDeleteIntent(this))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(
                push_id /* ID of notification */,
                notificationBuilder.build());
    }

    private PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction("push_cancelled");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
