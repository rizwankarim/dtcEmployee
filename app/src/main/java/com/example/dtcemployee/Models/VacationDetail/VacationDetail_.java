
package com.example.dtcemployee.Models.VacationDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VacationDetail_ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("beginning_date")
    @Expose
    private String beginningDate;
    @SerializedName("ending_date")
    @Expose
    private String endingDate;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Type")
    @Expose
    private String type;

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

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(String beginningDate) {
        this.beginningDate = beginningDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
