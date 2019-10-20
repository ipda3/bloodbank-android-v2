
package com.reda.yehia.bloodbankv2.data.model.notification.getNotificationCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.reda.yehia.bloodbankv2.data.model.notification.getAllNotification.NotificationPagination;

public class NotificationCount {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NotificationCountData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NotificationCountData getData() {
        return data;
    }

    public void setData(NotificationCountData data) {
        this.data = data;
    }

}
