package com.library.aritic.PushNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.library.aritic.AriticLogger;
import com.library.aritic.HitApi.HitApiPushNotification;

public class ActionReceiver extends BroadcastReceiver {
    private static final String ACTION_BUTTON_CLICKED = "actionButtonClicked";
    private static final String ACTION_BUTTON_DISMISSED = "actionButtonDismissed";
    private static final String ACTION_BUTTON_ID = "actionbButtonId";
    private static final String ACTION_URI = "actionURI";
    private String channelId = "channel FCM";
    private String testTitle;
    public static final String BUNDLE_KEY_ANDROID_NOTIFICATION_ID = "androidNotificationId";
    @Override
    public void onReceive(Context context, Intent intent) {
//        int push_id = intent.getIntExtra("push_id",-1);
//        log("Push Id: " + push_id);
        log("Inside Action Receiver");
        log("Action " + intent.getAction());
        int pushId = intent.getIntExtra(BUNDLE_KEY_ANDROID_NOTIFICATION_ID, 0);
        int buttonId = intent.getIntExtra(ACTION_BUTTON_ID, 0);
        String URL = intent.getStringExtra(ACTION_URI);
        handlePushClicked(pushId, buttonId, context);
        if(URL.startsWith("https")) {
            redirectUserToUrl(URL,context);
        }


        log("Push Id: " + pushId + " , buttonId: " + buttonId + ", URL:" + URL);

    }
    public void log(String msg) {
        AriticLogger.Log(msg);
    }


    private void redirectUserToUrl(String url, Context context) {
        Intent intent= new Intent("com.library.aritic.Ui.WebViewActivity");
        intent.putExtra("URL", url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }




    private void handlePushClicked(int push_id, int buttonId, Context context) {
        HitApiPushNotification hitApiPushNotification = new HitApiPushNotification();
        hitApiPushNotification.hitButtonClickApi(push_id, "PushButtonClick", buttonId);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(push_id);

    }
}
