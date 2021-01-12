
package com.example.dtcemployee.Models.GetAllLocation;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllLocation {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("All_Locations")
    @Expose
    private List<AllLocation> allLocations = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AllLocation> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(List<AllLocation> allLocations) {
        this.allLocations = allLocations;
    }

}
