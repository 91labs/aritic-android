
package com.library.aritic.Data.Model.Response.InAppResponseNew;


import android.content.res.ColorStateList;

public class Properties {

    private String text;
    private String textSize;
    private String textColor;
    private String bgColor;
    private String imageURL;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Properties withText(String text) {
        this.text = text;
        return this;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public Properties withTextSize(String textSize) {
        this.textSize = textSize;
        return this;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Properties withTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public Properties withBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Properties withImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

}
