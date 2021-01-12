
package com.example.dtcemployee.Models.UpDateVacationRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeVacation {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("beginning_date")
    @Expose
    private String beginningDate;
    @SerializedName("Reason")
    @Expose
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(String beginningDate) {
        this.beginningDate = beginningDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
