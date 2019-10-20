
package com.reda.yehia.bloodbankv2.data.model.notification.getNotificationCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.reda.yehia.bloodbankv2.data.model.notification.getAllNotification.NotificationPagination;

public class NotificationCountData {

    @SerializedName("notifications-count")
    @Expose
    private Integer notificationsCount;

    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(Integer notificationsCount) {
        this.notificationsCount = notificationsCount;
    }
}
