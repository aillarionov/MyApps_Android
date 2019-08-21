package ru.gastroafisha.MyApps.Utils.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 16.03.2018.
 */

public class NotificationNews {

    @SerializedName("orgId") @Expose // GSON
    Integer orgId;

    @SerializedName("catalogId") @Expose // GSON
    Integer catalogId;

    @SerializedName("itemId") @Expose // GSON
    Integer itemId;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
