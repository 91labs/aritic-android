package com.library.aritic.Data.Model.Request;

public class PushNotificationEventRequest {
        private String object;
        private int object_id;
        private String event;

    public PushNotificationEventRequest(String object, int objectId, String event) {
        this.object = object;
        this.object_id = objectId;
        this.event = event;
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
