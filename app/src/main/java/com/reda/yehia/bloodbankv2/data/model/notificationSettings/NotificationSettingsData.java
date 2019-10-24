
package com.reda.yehia.bloodbankv2.data.model.notificationSettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationSettingsData {

    @SerializedName("governorates")
    @Expose
    private List<String> governorates = null;
    @SerializedName("blood_types")
    @Expose
    private List<String> bloodTypes = null;

    public List<String> getGovernorates() {
        return governorates;
    }

    public void setGovernorates(List<String> governorates) {
        this.governorates = governorates;
    }

    public List<String> getBloodTypes() {
        return bloodTypes;
    }

    public void setBloodTypes(List<String> bloodTypes) {
        this.bloodTypes = bloodTypes;
    }

}
