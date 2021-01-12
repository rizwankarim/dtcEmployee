
package com.example.dtcemployee.Models.GetEmployeeLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeLocation {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("Location")
    @Expose
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
