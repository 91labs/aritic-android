
package com.library.aritic.Data.Model.Response.InAppResponse;


import com.library.aritic.Data.Model.Response.InAppResponse.NestedClasses.Data;

public class InAppResponse {

    private Boolean success;
    private Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public InAppResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public InAppResponse withData(Data data) {
        this.data = data;
        return this;
    }

}
