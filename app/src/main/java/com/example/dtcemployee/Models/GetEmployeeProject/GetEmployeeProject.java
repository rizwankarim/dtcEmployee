
package com.example.dtcemployee.Models.GetEmployeeProject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmployeeProject {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Employee_Project")
    @Expose
    private List<EmployeeProject> employeeProject = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmployeeProject> getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(List<EmployeeProject> employeeProject) {
        this.employeeProject = employeeProject;
    }

}
