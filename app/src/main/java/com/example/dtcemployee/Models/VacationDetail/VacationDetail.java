
package com.example.dtcemployee.Models.VacationDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VacationDetail {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Vacation_detail")
    @Expose
    private List<VacationDetail_> vacationDetail = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<VacationDetail_> getVacationDetail() {
        return vacationDetail;
    }

    public void setVacationDetail(List<VacationDetail_> vacationDetail) {
        this.vacationDetail = vacationDetail;
    }

}
