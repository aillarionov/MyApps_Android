package ru.gastroafisha.MyApps.Model.Org;

import android.arch.persistence.room.Embedded;

import ru.gastroafisha.MyApps.Model.Item.CatalogAbstractFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;

/**
 * Created by alex on 17.01.2018.
 */

public class OrgConfigOrgModel {
    @Embedded
    private OrgModel org;

    @Embedded
    private OrgConfigModel orgConfig;

    public OrgModel getOrg() {
        return org;
    }

    public void setOrg(OrgModel org) {
        this.org = org;
    }

    public OrgConfigModel getOrgConfig() {
        return orgConfig;
    }

    public void setOrgConfig(OrgConfigModel orgConfig) {
        this.orgConfig = orgConfig;
    }
}
