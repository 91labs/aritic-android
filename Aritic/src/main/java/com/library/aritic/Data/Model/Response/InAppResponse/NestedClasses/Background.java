
package com.library.aritic.Data.Model.Response.InAppResponse.NestedClasses;


public class Background {

    private String imageURL;
    private String color;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Background withImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Background withColor(String color) {
        this.color = color;
        return this;
    }

}
