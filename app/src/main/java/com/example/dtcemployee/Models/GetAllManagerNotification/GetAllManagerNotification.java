
package com.example.dtcemployee.Models.GetAllManagerNotification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllManagerNotification {

    @SerializedName("response_status")
    @Expose
    private String responseStatus;
    @SerializedName("Notifications")
    @Expose
    private List<Notification> notifications = null;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}
