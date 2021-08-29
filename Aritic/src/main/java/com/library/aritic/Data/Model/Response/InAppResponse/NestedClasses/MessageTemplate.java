
package com.library.aritic.Data.Model.Response.InAppResponse.NestedClasses;

import java.util.ArrayList;
import java.util.List;

public class MessageTemplate {

    private String title;
    private String subtitle;
    private String centerImageURL;
    private List<Button> buttons = new ArrayList<Button>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MessageTemplate withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public MessageTemplate withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getCenterImageURL() {
        return centerImageURL;
    }

    public void setCenterImageURL(String centerImageURL) {
        this.centerImageURL = centerImageURL;
    }

    public MessageTemplate withCenterImageURL(String centerImageURL) {
        this.centerImageURL = centerImageURL;
        return this;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public MessageTemplate withButtons(List<Button> buttons) {
        this.buttons = buttons;
        return this;
    }

}
