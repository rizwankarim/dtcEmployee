package com.example.dtcemployee.Models.GetAllEmployeesNotification;

import com.example.dtcemployee.Models.GetSubEmployeeAllNotification.Notification;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllEmployeesNotification {
    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Notifications")
    @Expose
    private List<EmpNotification> notifications = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<EmpNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<EmpNotification> notifications) {
        this.notifications = notifications;
    }
}
