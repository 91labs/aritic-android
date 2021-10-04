package com.library.aritic.Data.Model.Request;

import androidx.annotation.Nullable;

public class PushNotificationEventRequest {
        private String object;
        private int object_id;
        private String event;
       @Nullable private int buttonId;

    public PushNotificationEventRequest(String object, int objectId, String event) {
        this.object = object;
        this.object_id = objectId;
        this.event = event;
    }

    public PushNotificationEventRequest(String object, int objectId, String event, int buttonId) {
        this.object = object;
        this.object_id = objectId;
        this.event = event;
        this.buttonId = buttonId;
    }

    public String getObject() {
        return object;
    }

    public int getObjectId() {
        return object_id;
    }

    public String getEvent() {
        return event;
    }
}
