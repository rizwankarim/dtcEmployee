
package com.example.dtcemployee.Models.EmployeeAttendence;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeAttenndence {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Check_out_time")
    @Expose
    private List<EmployeeCheckOutTime> employeeCheckOutTime = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeCheckOutTime> getEmployeeCheckOutTime() {
        return employeeCheckOutTime;
    }

    public void setEmployeeCheckOutTime(List<EmployeeCheckOutTime> employeeCheckOutTime) {
        this.employeeCheckOutTime = employeeCheckOutTime;
    }

}
