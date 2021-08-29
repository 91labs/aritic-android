
package com.library.aritic.Data.Model.Response.InAppResponseNew;

import java.util.ArrayList;
import java.util.List;

public class InAppMessage {

    private String messageId;
    private String companyId;
    private String messageType;
    private String position;
    private Background background;
    private List<MessageTemplate> messageTemplate = new ArrayList<MessageTemplate>();
    private List<Button> buttons = new ArrayList<Button>();

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public InAppMessage withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public InAppMessage withCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public InAppMessage withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public InAppMessage withPosition(String position) {
        this.position = position;
        return this;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public InAppMessage withBackground(Background background) {
        this.background = background;
        return this;
    }

    public List<MessageTemplate> getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(List<MessageTemplate> messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public InAppMessage withMessageTemplate(List<MessageTemplate> messageTemplate) {
        this.messageTemplate = messageTemplate;
        return this;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public InAppMessage withButtons(List<Button> buttons) {
        this.buttons = buttons;
        return this;
    }

}
