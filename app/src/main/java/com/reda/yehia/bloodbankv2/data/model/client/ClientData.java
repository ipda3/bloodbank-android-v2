
package com.reda.yehia.bloodbankv2.data.model.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private ClientFullData client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ClientFullData getClient() {
        return client;
    }

    public void setClient(ClientFullData client) {
        this.client = client;
    }

}
