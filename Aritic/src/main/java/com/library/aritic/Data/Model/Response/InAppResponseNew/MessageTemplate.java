
package com.library.aritic.Data.Model.Response.InAppResponseNew;


public class MessageTemplate {

    private String type;
    private Properties properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageTemplate withType(String type) {
        this.type = type;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public MessageTemplate withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

}
