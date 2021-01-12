
package com.example.dtcemployee.Models.AddVocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddVocation {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_Vacation_Id")
    @Expose
    private Integer addVacationId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAddVacationId() {
        return addVacationId;
    }

    public void setAddVacationId(Integer addVacationId) {
        this.addVacationId = addVacationId;
    }

}
