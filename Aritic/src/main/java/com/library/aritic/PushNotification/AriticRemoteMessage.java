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
import com.library.aritic.R;

import org.json.JSONObject;

import java.util.List;


/** A wrapper class over remote message, enables greater
 *  flexiblity for Aritic Team to Modify / add future Fields
 * */

public class AriticRemoteMessage{


    // The Actual Message from Firebase
    private RemoteMessage message;


    // Individual Components in Message
    private String channelId = "channel FCM";
    private MessageCallbacks mCallbacks;
    private int androidNotificationId;

    private String notificationId;
    private String templateName;
    private String templateId;
    private String title;
    private String body;
    private JSONObject additionalData;
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
    private List<ActionButton> actionButtons;
    private String fromProjectNumber;
    private BackgroundImageLayout backgroundImageLayout;
    private String collapseId;
    private int priority;
    private String rawPayload;


    public AriticRemoteMessage(RemoteMessage remoteMessage) {
        this.message = remoteMessage;
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

    public  void complete(AriticRemoteMessage message) {
        if(message != null) {
            // Nobody did anyting.
            // show the message
            mCallbacks.showPushMessage(message);

        } else {
            // Message has been Sent as Null,
            // do not do any thing
        }

    }

    private void buildRemoteMessage(AriticRemoteMessage message) {

    }

    public void log(String msg) {
        Log.d("PUSH : ", msg);
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
