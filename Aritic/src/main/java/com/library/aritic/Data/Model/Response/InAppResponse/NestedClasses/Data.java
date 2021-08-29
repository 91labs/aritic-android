
package com.library.aritic.Data.Model.Response.InAppResponse.NestedClasses;


public class Data {

    private Boolean showInAppMessage;
    private InAppMessage inAppMessage;

    public Boolean getShowInAppMessage() {
        return showInAppMessage;
    }

    public void setShowInAppMessage(Boolean showInAppMessage) {
        this.showInAppMessage = showInAppMessage;
    }

    public Data withShowInAppMessage(Boolean showInAppMessage) {
        this.showInAppMessage = showInAppMessage;
        return this;
    }

    public InAppMessage getInAppMessage() {
        return inAppMessage;
    }

    public void setInAppMessage(InAppMessage inAppMessage) {
        this.inAppMessage = inAppMessage;
    }

    public Data withInAppMessage(InAppMessage inAppMessage) {
        this.inAppMessage = inAppMessage;
        return this;
    }

}
