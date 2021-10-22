package com.library.aritic.PushNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.library.aritic.AriticLogger;
import com.library.aritic.R;
import com.library.aritic.Util.Util.SimpleCountDownTimer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


/** A wrapper class over remote message, enables greater
 *  flexiblity for Aritic Team to Modify / add future Fields
 * */

public class AriticRemoteMessage implements SimpleCountDownTimer.OnCountDownListener {


    // The Actual Message from Firebase
    private RemoteMessage message;
    private  AriticRemoteMessage ariticRemoteMessage;
    // Individual Components in Message
    private String channelId = "channel FCM";
    private MessageCallbacks mCallbacks;
    private int androidNotificationId;

    private int notificationId;
    private String templateName;
    private String templateId;
    private String title;
    private String body;
    private String smallIcon;
    private String largeIcon;
    private String bigPicture;
    private String smallIconAccentColor;
    private String launchURL;
    private String sound;
    private String ledColor;
    private int lockScreenVisibility = 1;
    private String groupKey;
    private String groupMessage;
    private JSONArray actionButtons;
    private JSONObject customKeys;
    private String fromProjectNumber;
    private BackgroundImageLayout backgroundImageLayout;
    private String collapseId;
    private int priority;
    private String rawPayload;
    SimpleCountDownTimer timer = null;
    private boolean processing = false;

    public AriticRemoteMessage(RemoteMessage remoteMessage) {
        // A New Remote message has been come
        this.message = remoteMessage;
        parseRemoteMessage();
    }



    public void parseRemoteMessage() {
        try {
            if(message != null ) {
                Map<String, String> dataObject = message.getData();
                JSONObject notifObject = new JSONObject(dataObject);
                this.notificationId = notifObject.optInt("pushId");
                this.title = notifObject.optString("title");
                this.body = notifObject.optString("subTitle");
                this.smallIcon = notifObject.optString("smallIcon");
                this.largeIcon = notifObject.optString("largeImage");
                this.channelId = notifObject.optString("channeId");

                // parseAction Buttons
                String actionButtons = notifObject.optString("actionButtons");
                JSONArray buttons = new JSONArray(actionButtons);
                this.actionButtons = buttons;

                // parseCustomKeys
                String customeKeysText = notifObject.optString("customKeys");
                this.customKeys = new JSONObject(customeKeysText);
//                timer = new SimpleCountDownTimer(0,25,1,this);
//                timer.start(false);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public RemoteMessage getRemoteMessage() {
        if(this.message!=null) {
            return this.message;
        }
        return null;
    }

    public void setMessageCallbacks(MessageCallbacks m) {
        if(m!= null) {
            this.mCallbacks = m;
        }


    }


    /** complete function is called from Client library - which means developer has called this to show either PMessage or no, in either way it
     * calling complete means processing and 25 seconds timer is destroyed
     *
     * */
    public  void complete(AriticRemoteMessage message) {
        this.processing = true;
        if(message != null) {
            // Nobody did anyting.
            // show the message
            AriticLogger.Log("Callbacks is there");

            mCallbacks.showPushMessage(message);

        } else {
            // Message has been Sent as Null,
            AriticLogger.Log("No Callbacks");

            // destroy Timer
            // do not do any thing
        }

    }

    private void buildRemoteMessage(AriticRemoteMessage message) {

    }

    public void log(String msg) {
        Log.d("PUSH : ", msg);
    }

    @Override
    public void onCountDownActive(String time) {

    }

    @Override
    public void onCountDownFinished() {
        // 25 seconds is finished.
        // see if already messages are being processed
        // or receoved calls backs from
        if(processing) {
            this.timer = null;
        } else {
            // Inactiion, show show the timer
            MessagingService s = new MessagingService();
            s.showNotification(this.message);

        }
    }


    public interface MessageCallbacks {
        public void showPushMessage(AriticRemoteMessage message);
    }


    /**
     * List of action buttons on the notification.
     */
    public static class ActionButton {
        private String id;
        private String text;
        private String icon;

        public ActionButton() {}

        public ActionButton(JSONObject jsonObject) {
            id = jsonObject.optString("id");
            text = jsonObject.optString("text");
            icon = jsonObject.optString("icon");
        }

        public ActionButton(String id, String text, String icon) {
            this.id = id;
            this.text = text;
            this.icon = icon;
        }

        public JSONObject toJSONObject() {
            JSONObject json = new JSONObject();
            try {
                json.put("id", id);
                json.put("text", text);
                json.put("icon", icon);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }

            return json;
        }

        public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String getIcon() {
            return icon;
        }
    }

    /**
     * If a background image was set, this object will be available.
     */
    public static class BackgroundImageLayout {
        private String image;
        private String titleTextColor;
        private String bodyTextColor;

        public String getImage() {
            return image;
        }

        public String getTitleTextColor() {
            return titleTextColor;
        }

        public String getBodyTextColor() {
            return bodyTextColor;
        }
    }

}
