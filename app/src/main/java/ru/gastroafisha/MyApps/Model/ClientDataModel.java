package ru.gastroafisha.MyApps.Model;


import java.util.List;

import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;


/**
 * Created by alex on 22.12.2017.
 */

public class ClientDataModel {

    private String tokenId;
    private String adId;
    private List<OrgConfigModel> orgConfigs;

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

    public List<OrgConfigModel> getOrgConfigs() {
        return orgConfigs;
    }

    public void setOrgConfigs(List<OrgConfigModel> orgConfigs) {
        this.orgConfigs = orgConfigs;
    }
}
