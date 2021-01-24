package com.example.dtcemployee.Models.EmployeePic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeePic {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;

    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
