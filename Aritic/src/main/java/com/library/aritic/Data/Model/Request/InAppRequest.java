package com.library.aritic.Data.Model.Request;

public class InAppRequest {

    private String pageName;

    public InAppRequest(String pageName) {
        this.pageName = pageName;
    }

    public String getPagename() {
        return pageName;
    }

    public void setPagename(String pagename) {
        this.pageName = pageName;
    }
}
