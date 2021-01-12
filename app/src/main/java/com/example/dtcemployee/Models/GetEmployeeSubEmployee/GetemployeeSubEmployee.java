
package com.example.dtcemployee.Models.GetEmployeeSubEmployee;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetemployeeSubEmployee {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("All_Sub_Employees")
    @Expose
    private List<AllSubEmployee> allSubEmployees = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AllSubEmployee> getAllSubEmployees() {
        return allSubEmployees;
    }

    public void setAllSubEmployees(List<AllSubEmployee> allSubEmployees) {
        this.allSubEmployees = allSubEmployees;
    }

}
