
package com.example.dtcemployee.Models.GetAllVacationTypye;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllVacation {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Vacation_Type")
    @Expose
    private List<VacationType> vacationType = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<VacationType> getVacationType() {
        return vacationType;
    }

    public void setVacationType(List<VacationType> vacationType) {
        this.vacationType = vacationType;
    }

}
