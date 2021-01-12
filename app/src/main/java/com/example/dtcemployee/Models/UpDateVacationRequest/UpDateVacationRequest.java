
package com.example.dtcemployee.Models.UpDateVacationRequest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpDateVacationRequest {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Vacations")
    @Expose
    private List<EmployeeVacation> employeeVacations = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeVacation> getEmployeeVacations() {
        return employeeVacations;
    }

    public void setEmployeeVacations(List<EmployeeVacation> employeeVacations) {
        this.employeeVacations = employeeVacations;
    }

}
