
package com.example.dtcemployee.Models.AddReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddReport {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Add_Report_Id")
    @Expose
    private Integer addReportId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAddReportId() {
        return addReportId;
    }

    public void setAddReportId(Integer addReportId) {
        this.addReportId = addReportId;
    }

}
