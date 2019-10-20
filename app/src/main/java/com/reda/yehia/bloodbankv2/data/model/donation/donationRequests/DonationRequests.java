
package com.reda.yehia.bloodbankv2.data.model.donation.donationRequests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationRequests {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DonationRequestsPaginatuion data;

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

    public DonationRequestsPaginatuion getData() {
        return data;
    }

    public void setData(DonationRequestsPaginatuion data) {
        this.data = data;
    }

}
