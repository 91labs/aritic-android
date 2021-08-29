
package com.library.aritic.Data.Model.Response.InAppResponse.NestedClasses;


public class InAppMessage {

    private Integer messageId;
    private Integer companyId;
    private String messageType;
    private String position;
    private Background background;
    private MessageTemplate messageTemplate;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public InAppMessage withMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public InAppMessage withCompanyId(Integer companyId) {
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

    public MessageTemplate getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(MessageTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public InAppMessage withMessageTemplate(MessageTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
        return this;
    }

}
