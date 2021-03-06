
package com.example.dtcemployee.Models.CheckIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckIn {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;

    @SerializedName("c_id")
    @Expose
    private String c_id;

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

}
