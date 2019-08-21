package ru.gastroafisha.MyApps.Model.Org;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ru.gastroafisha.MyApps.Model.ImageModel;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "orgconfigs")
public class OrgConfigModel {

    @PrimaryKey @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "isVisitor") // SQLite
    private Boolean isVisitor;

    @NonNull @ColumnInfo(name = "isPresenter") // SQLite
    private Boolean isPresenter;

    @NonNull @ColumnInfo(name = "receiveNotifications") // SQLite
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
