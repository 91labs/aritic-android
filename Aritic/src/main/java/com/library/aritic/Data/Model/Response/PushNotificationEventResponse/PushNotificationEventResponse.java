package com.library.aritic.Data.Model.Response.PushNotificationEventResponse;

public class PushNotificationEventResponse {
    private String success;
    private String message;

    public PushNotificationEventResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
