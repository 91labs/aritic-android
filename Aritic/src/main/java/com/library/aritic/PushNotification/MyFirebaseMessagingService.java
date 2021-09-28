package com.library.aritic.PushNotification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.library.aritic.AppHelpers;
import com.library.aritic.Aritic;
import com.library.aritic.AriticLogger;
import com.library.aritic.InAppRegisteration.AriticRegisteration;
import com.library.aritic.R;
import com.library.aritic.SharedPref.SharedPref;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService implements AriticRemoteMessage.MessageCallbacks {

    private static final String ACTION_BUTTON_CLICKED = "actionButtonClicked";
    private static final String ACTION_BUTTON_DISMISSED = "actionButtonDismissed";
    private static final String ACTION_BUTTON_ID = "actionbButtonId";
    private static final String ACTION_URI = "actionURI";
    private String channelId = "channel FCM";
    private String testTitle;
    public static final String BUNDLE_KEY_ANDROID_NOTIFICATION_ID = "androidNotificationId";

    private  static AriticPushListener pushListener = null;
    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
        Log.d("REGISTER: onNewToken " ,"A new token");
//        registerNewToken(s);
    }

    public  void setPushListener(AriticPushListener p) {
        AriticLogger.Log("Setting Push Listener ");
        if(pushListener == null)
            pushListener = p;
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
        log("Received PushMessage from Firebase");
        AriticRemoteMessage message = new AriticRemoteMessage(remoteMessage);

        if(pushListener != null) {
            pushListener.onPushMessageReceived(message);
        } else {
            AriticLogger.Log("No Listener Found");
        }




//        sendNotification(remoteMessage);
    }

    @NonNull
    @Override
    protected Intent getStartCommandIntent(@NonNull Intent intent) {
        AriticLogger.Log("getStarted Ccommand");
        return super.getStartCommandIntent(intent);

    }

    @Override
    public void handleIntent(@NonNull Intent intent) {
        AriticLogger.Log("Handle Intent");
        super.handleIntent(intent);

    }

    @Override
    public boolean handleIntentOnMainThread(@NonNull Intent intent) {
        AriticLogger.Log("Handle Intent on Main Thread");
        return super.handleIntentOnMainThread(intent);

    }

    public  void complete(AriticRemoteMessage message) {


    }

    private  void showNotification(RemoteMessage remoteMessage) {
//        int push_id = Integer.parseInt(
//                Objects.requireNonNull(remoteMessage.getData().get("push_id")));
        log("Remote MessageId " + remoteMessage.getMessageId());
        int push_id  = 123123;
        log("Push ID " + push_id);



        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, remoteMessage.getNotification().getChannelId())
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setSmallIcon(getDrawableId(remoteMessage.getNotification().getIcon()))
                .setLargeIcon(getLargeIcon(remoteMessage.getNotification().getImageUrl().toString()))
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(getActionIntent(this,push_id))
                .setDeleteIntent(getDeleteIntent(this, push_id))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true);

            String data = "{\"notificationId\":312,\"actionButtons\":[{\"id\":123123,\"name\":\"Button1\",\"action\":\"https://someLink\"},{\"id\":211,\"name\":\"Button2\",\"action\":\"https://someLink\"}],\"moreData\":\"moreDataHere\"}";
//            addActionButtons(notificationBuilder,remoteMessage.getData().toString());
          addActionButtons(notificationBuilder, data);




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

//    http://localhost:5000/?token=d1uZHNAiQ427Le1d3G3GDj:APA91bGUpQnEQJTVTzscJrsqVU0y-PZmbQsR_lqwY8Qzw5nSZAEYy_Bo47MdEKH3ZpB54WEZpvzVNSK5Mvd8M__XmLsb22MQTamU8vEuK93BFB1V_4zYPfpwp0lCR6eVc7NQL9OIsL7_

    private void addActionButtons(NotificationCompat.Builder notificationBuilder, String data) {
        try {
            JSONObject customJson = new JSONObject(data);

            int notificationId = 0;
            if(customJson.has("notificationId")) {
                throw new IllegalAccessException("NotificationId is missing");
            }

            notificationId = customJson.getInt("notificationId");

            if (!customJson.has("actionButtons"))
                return;

            JSONArray buttons = customJson.optJSONArray("actionButtons");

            for (int i = 0; i < buttons.length(); i++) {
                JSONObject button = buttons.optJSONObject(i);

                Intent buttonIntent = getNewBaseIntent(notificationId,button.optInt("id"), button.optString("actionURI"));
                buttonIntent.putExtra("action_button", true);
                PendingIntent buttonPIntent = getNewActionPendingIntent(notificationId, buttonIntent);

                int buttonIcon = 0;
                if (button.has("icon"))
                    buttonIcon = getResourceIcon(button.optString("icon"));

                notificationBuilder.addAction(buttonIcon, button.optString("text"), buttonPIntent);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }




    private Bitmap getLargeIcon(String IconName) {
        Bitmap bitmap = getBitmap(IconName);
        if (bitmap == null)
            bitmap = getBitmapFromAssetsOrResourceName("ic_onesignal_large_icon_default");

        if (bitmap == null)
            return null;

        return resizeBitmapForLargeIconArea(bitmap);
    }


    private  Bitmap resizeBitmapForLargeIconArea(Bitmap bitmap) {
        if (bitmap == null)
            return null;

        try {

            Resources r = getBaseContext().getResources();
            int systemLargeIconHeight = (int) r.getDimension(android.R.dimen.notification_large_icon_height);
            int systemLargeIconWidth = (int) r.getDimension(android.R.dimen.notification_large_icon_width);
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            if (bitmapWidth > systemLargeIconWidth || bitmapHeight > systemLargeIconHeight) {
                int newWidth = systemLargeIconWidth, newHeight = systemLargeIconHeight;
                if (bitmapHeight > bitmapWidth) {
                    float ratio = (float) bitmapWidth / (float) bitmapHeight;
                    newWidth = (int) (newHeight * ratio);
                } else if (bitmapWidth > bitmapHeight) {
                    float ratio = (float) bitmapHeight / (float) bitmapWidth;
                    newHeight = (int) (newWidth * ratio);
                }

                return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
            }
        } catch (Throwable t) {}

        return bitmap;
    }

    private  PendingIntent getNewActionPendingIntent(int requestCode, Intent intent) {
        return PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private  Intent getNewBaseIntent(int notificationId, int buttonId, String actionURI) {
        return new Intent(this, NotificationReceiver.class)
                .putExtra(BUNDLE_KEY_ANDROID_NOTIFICATION_ID, notificationId)
                .putExtra(ACTION_BUTTON_ID, buttonId)
                .putExtra(ACTION_URI, actionURI)
                .setAction(ACTION_BUTTON_CLICKED)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    @Override
    public void showPushMessage(AriticRemoteMessage message) {
        if(message != null) {
            RemoteMessage msg = message.getRemoteMessage();
            buildNotification(message.getRemoteMessage());
        } else {
            // Message has been Sent as Null,
            // do not do any thing
        }
    }



    private  Bitmap getBitmapFromAssetsOrResourceName(String bitmapStr) {
        try {
            Context c = getBaseContext();
            Bitmap bitmap = null;

            try {
                bitmap = BitmapFactory.decodeStream(c.getAssets().open(bitmapStr));
            } catch (Throwable t) {}

            if (bitmap != null)
                return bitmap;

            final List<String> image_extensions = Arrays.asList(".png", ".webp", ".jpg", ".gif", ".bmp");
            for (String extension : image_extensions) {
                try {
                    bitmap = BitmapFactory.decodeStream(c.getAssets().open(bitmapStr + extension));
                } catch (Throwable t) {}
                if (bitmap != null)
                    return bitmap;
            }

            int bitmapId = getResourceIcon(bitmapStr);
            if (bitmapId != 0)
                return BitmapFactory.decodeResource(c.getResources(), bitmapId);
        } catch (Throwable t) {}

        return null;
    }


    private void buildNotification(RemoteMessage remoteMessage) {
        attachActionButtons();
    }

    private void attachActionButtons() {

    }



    private  Bitmap getBitmapFromURL(String location) {
        try {
            return BitmapFactory.decodeStream(new URL(location).openConnection().getInputStream());
        } catch (Throwable t) {
            AriticLogger.Log("Could Not get Image URL");
        }

        return null;
    }

    private  Bitmap getBitmap(String name) {
        if (name == null)
            return null;
        String trimmedName = name.trim();

        if (trimmedName.startsWith("http://") || trimmedName.startsWith("https://"))
            return getBitmapFromURL(trimmedName);

        return getBitmapFromAssetsOrResourceName(name);
    }



//
//    private  Bitmap getBitmapFromAssetsOrResourceName(String bitmapStr) {
//        try {
//            Bitmap bitmap = null;
//
//            try {
//                bitmap = BitmapFactory.decodeStream(currentContext.getAssets().open(bitmapStr));
//            } catch (Throwable t) {}
//
//            if (bitmap != null)
//                return bitmap;
//
//            final List<String> image_extensions = Arrays.asList(".png", ".webp", ".jpg", ".gif", ".bmp");
//            for (String extension : image_extensions) {
//                try {
//                    bitmap = BitmapFactory.decodeStream(currentContext.getAssets().open(bitmapStr + extension));
//                } catch (Throwable t) {}
//                if (bitmap != null)
//                    return bitmap;
//            }
//
//            int bitmapId = getResourceIcon(bitmapStr);
//            if (bitmapId != 0)
//                return BitmapFactory.decodeResource(contextResources, bitmapId);
//        } catch (Throwable t) {}
//
//        return null;
//    }

    private int getResourceIcon(String iconName) {
        if (iconName == null)
            return 0;

        String trimmedIconName = iconName.trim();
        if (!AppHelpers.isValidResourceName(trimmedIconName))
            return 0;

        int notificationIcon = getDrawableId(trimmedIconName);
        if (notificationIcon != 0)
            return notificationIcon;

        // Get system icon resource
        try {
            return Drawable.class.getField(iconName).getInt(null);
        } catch (Throwable t) {}

        return 0;
    }

    private  int getSmallIconId(JSONObject fcmJson) {
        int notificationIcon = getResourceIcon(fcmJson.optString("sicon", null));
        if (notificationIcon != 0)
            return notificationIcon;

        return getDefaultSmallIconId();
    }

    private  int getDefaultSmallIconId() {
        int notificationIcon = getDrawableId("ic_stat_onesignal_default");
        if (notificationIcon != 0)
            return notificationIcon;

        notificationIcon = getDrawableId("corona_statusbar_icon_default");
        if (notificationIcon != 0)
            return notificationIcon;

        notificationIcon = getDrawableId("ic_os_notification_fallback_white_24dp");
        if (notificationIcon != 0)
            return notificationIcon;

        return android.R.drawable.btn_default_small;
    }

    public int getDrawableId(String name) {
        Context c  = getBaseContext();
        String packageName = c.getPackageName();
        Resources r  = c.getResources();
        return r.getIdentifier(name, "drawable", packageName);

    }


    public interface AriticPushListener {
        public void onPushMessageReceived(AriticRemoteMessage message);
    }
}
