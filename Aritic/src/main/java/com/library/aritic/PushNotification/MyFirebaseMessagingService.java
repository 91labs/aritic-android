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
        log("Push Message Received ");
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
//        int push_id = Integer.parseInt(
//                Objects.requireNonNull(remoteMessage.getData().get("push_id")));
        log("Remote MessageId " + remoteMessage.getMessageId());
        int push_id  = 123123;
        log("Push ID " + push_id);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                 .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(getActionIntent(this,push_id))
                .setDeleteIntent(getDeleteIntent(this, push_id))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);
        log("Notiifcation building");
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

    private PendingIntent getDeleteIntent(Context context, int push_id) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction("push_cancelled");
        intent.putExtra("push_id", push_id);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private PendingIntent getActionIntent(Context context, int push_id) {
        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("push_id", push_id);
        broadcastIntent.setAction("push_clicked");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return actionIntent;
    }

    public void log(String msg) {
        Log.d("PUSH : ", msg);
    }
}
