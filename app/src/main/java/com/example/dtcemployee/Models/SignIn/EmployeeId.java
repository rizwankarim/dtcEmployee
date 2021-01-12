
package com.example.dtcemployee.Models.SignIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeId {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("Manager_Name")
    @Expose
    private String managerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }


    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

}
