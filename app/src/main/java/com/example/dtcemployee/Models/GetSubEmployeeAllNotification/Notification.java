
package com.example.dtcemployee.Models.GetSubEmployeeAllNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("sub_emp_id")
    @Expose
    private String subEmpId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("notifications")
    @Expose
    private String notifications;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("Sub_Employee_Name")
    @Expose
    private String subEmployeeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSubEmpId() {
        return subEmpId;
    }

    public void setSubEmpId(String subEmpId) {
        this.subEmpId = subEmpId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubEmployeeName() {
        return subEmployeeName;
    }

    public void setSubEmployeeName(String subEmployeeName) {
        this.subEmployeeName = subEmployeeName;
    }

}
