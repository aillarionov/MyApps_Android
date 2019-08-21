package ru.gastroafisha.MyApps.Proxy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by alex on 22.12.2017.
 */

public class OrgConfigProxy {

    @SerializedName("orgId") @Expose // GSON
    private Integer orgId;

    @SerializedName("isVisitor") @Expose // GSON
    private Boolean isVisitor;

    @SerializedName("isPresenter") @Expose // GSON
    private Boolean isPresenter;

    @SerializedName("receiveNotifications") @Expose // GSON
    private Boolean receiveNotifications;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Boolean getVisitor() {
        return isVisitor;
    }

    public void setVisitor(Boolean visitor) {
        isVisitor = visitor;
    }

    public Boolean getPresenter() {
        return isPresenter;
    }

    public void setPresenter(Boolean presenter) {
        isPresenter = presenter;
    }

    public Boolean getReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(Boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }
}
