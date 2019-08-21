package ru.gastroafisha.MyApps.Proxy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by alex on 22.12.2017.
 */

public class ClientDataProxy {

    @SerializedName("token") @Expose // GSON
    private String tokenId;

    @SerializedName("ad") @Expose // GSON
    private String adId;

    @SerializedName("ostype") @Expose // GSON
    private String ostype = "android";

    @SerializedName("orgConfigs") @Expose // GSON
    private List<OrgConfigProxy> orgConfigs;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public List<OrgConfigProxy> getOrgConfigs() {
        return orgConfigs;
    }

    public void setOrgConfigs(List<OrgConfigProxy> orgConfigs) {
        this.orgConfigs = orgConfigs;
    }

    public String getOstype() {
        return ostype;
    }
}
