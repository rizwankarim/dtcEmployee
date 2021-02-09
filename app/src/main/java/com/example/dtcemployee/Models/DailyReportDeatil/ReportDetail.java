
package com.example.dtcemployee.Models.DailyReportDeatil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("User_Name")
    @Expose
    private String empId;
    @SerializedName("Project_Name")
    @Expose
    private String projectId;
    @SerializedName("Target")
    @Expose
    private String target;
    @SerializedName("Achievement")
    @Expose
    private String achievement;
    @SerializedName("Problems")
    @Expose
    private String problems;
    @SerializedName("Date_Time")
    @Expose
    private String dateTime;
    @SerializedName("Report_Image")
    @Expose
    private List<ReportImage> reportImage = null;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<ReportImage> getReportImage() {
        return reportImage;
    }

    public void setReportImage(List<ReportImage> reportImage) {
        this.reportImage = reportImage;
    }
}
