package com.library.aritic.Data.Model.Response.InAppResponse;

public class InAppEventResponse {
    private String success;
    private String message;

    public InAppEventResponse(String success, String message) {
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
