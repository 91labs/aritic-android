package com.library.aritic.Data.Model.Request;

public class InAppEventRequest {
        private String object;
        private String object_id;
        private String event;

    public InAppEventRequest(String object, String objectId, String event) {
        this.object = object;
        this.object_id = objectId;
        this.event = event;
    }

    public String getObject() {
        return object;
    }

    public String getObject_id() {
        return object_id;
    }

    public String getEvent() {
        return event;
    }
}
