
package com.example.dtcemployee.Models.GetEmployeeSubEmployee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllSubEmployee {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("sub_emp_id")
    @Expose
    private String subEmpId;
    @SerializedName("Sub_employee_name")
    @Expose
    private String subEmployeeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSubEmpId() {
        return subEmpId;
    }

    public void setSubEmpId(String subEmpId) {
        this.subEmpId = subEmpId;
    }

    public String getSubEmployeeName() {
        return subEmployeeName;
    }

    public void setSubEmployeeName(String subEmployeeName) {
        this.subEmployeeName = subEmployeeName;
    }

}
