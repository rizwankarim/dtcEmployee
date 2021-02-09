
package com.example.dtcemployee.Models.DailyReportDeatil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyReportDetail {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Report_Detail")
    @Expose
    private List<ReportDetail> reportDetail = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<ReportDetail> getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(List<ReportDetail> reportDetail) {
        this.reportDetail = reportDetail;
    }

}
