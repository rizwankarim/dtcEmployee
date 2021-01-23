package com.example.dtcemployee.Models.EndDateNotify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndDateNotify {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
