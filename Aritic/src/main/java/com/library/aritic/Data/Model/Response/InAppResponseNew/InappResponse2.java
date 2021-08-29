
package com.library.aritic.Data.Model.Response.InAppResponseNew;


public class InappResponse2 {

    private Boolean success;
    private Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public InappResponse2 withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public InappResponse2 withData(Data data) {
        this.data = data;
        return this;
    }

}
