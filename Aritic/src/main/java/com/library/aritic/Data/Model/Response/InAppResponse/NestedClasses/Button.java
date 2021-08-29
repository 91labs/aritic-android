
package com.library.aritic.Data.Model.Response.InAppResponse.NestedClasses;


public class Button {

    private String name;
    private String buttonId;
    private String actionRedirectType;
    private String actionValue;
    private String bgColor;
    private String textColor;
    private String textValue;
    private String size;
    private String alignment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button withName(String name) {
        this.name = name;
        return this;
    }

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public Button withButtonId(String buttonId) {
        this.buttonId = buttonId;
        return this;
    }

    public String getActionRedirectType() {
        return actionRedirectType;
    }

    public void setActionRedirectType(String actionRedirectType) {
        this.actionRedirectType = actionRedirectType;
    }

    public Button withActionRedirectType(String actionRedirectType) {
        this.actionRedirectType = actionRedirectType;
        return this;
    }

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }

    public Button withActionValue(String actionValue) {
        this.actionValue = actionValue;
        return this;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public Button withBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Button withTextColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Button withTextValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Button withSize(String size) {
        this.size = size;
        return this;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public Button withAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

}
