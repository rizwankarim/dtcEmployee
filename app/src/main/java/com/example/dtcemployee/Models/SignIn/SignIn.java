
package com.example.dtcemployee.Models.SignIn;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignIn {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Employee_Id")
    @Expose
    private List<EmployeeId> employeeId = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<EmployeeId> getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(List<EmployeeId> employeeId) {
        this.employeeId = employeeId;
    }

}
