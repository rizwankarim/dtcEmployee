
package com.example.dtcemployee.Models.GetEmployeeLocation;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmployeeLocation {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Locations")
    @Expose
    private List<EmployeeLocation> employeeLocations = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeLocation> getEmployeeLocations() {
        return employeeLocations;
    }

    public void setEmployeeLocations(List<EmployeeLocation> employeeLocations) {
        this.employeeLocations = employeeLocations;
    }

}
