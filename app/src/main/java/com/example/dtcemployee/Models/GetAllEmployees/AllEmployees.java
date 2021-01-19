package com.example.dtcemployee.Models.GetAllEmployees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllEmployees {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("manager_id")
    @Expose
    private String manager_id;

    @SerializedName("Employee_name")
    @Expose
    private String employeename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }
}
