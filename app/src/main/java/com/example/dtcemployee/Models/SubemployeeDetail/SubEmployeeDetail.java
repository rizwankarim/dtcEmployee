
package com.example.dtcemployee.Models.SubemployeeDetail;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubEmployeeDetail {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Employee_Detail")
    @Expose
    private List<EmployeeDetail> employeeDetail = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmployeeDetail> getEmployeeDetail() {
        return employeeDetail;
    }

    public void setEmployeeDetail(List<EmployeeDetail> employeeDetail) {
        this.employeeDetail = employeeDetail;
    }

}
