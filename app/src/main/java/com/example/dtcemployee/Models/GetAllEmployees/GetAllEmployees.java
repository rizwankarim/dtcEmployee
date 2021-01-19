package com.example.dtcemployee.Models.GetAllEmployees;

import com.example.dtcemployee.Models.GetEmployeeSubEmployee.AllSubEmployee;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllEmployees {
    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("All_Employees")
    @Expose
    private List<AllEmployees> allSubEmployees = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AllEmployees> getAllSubEmployees() {
        return allSubEmployees;
    }

    public void setAllSubEmployees(List<AllEmployees> allSubEmployees) {
        this.allSubEmployees = allSubEmployees;
    }
}
