package ru.gastroafisha.MyApps.Model.Catalog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.Date;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "catalogs", primaryKeys = {"orgId", "id"})
public class CatalogModel {

    @NonNull @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "id") // SQLite
    private Integer id;

    @NonNull @ColumnInfo(name = "type") // SQLite
    private CatalogType type;

    @NonNull @ColumnInfo(name = "lastChange") // SQLite
    private Date lastChange;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull Integer orgId) {
        this.orgId = orgId;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public CatalogType getType() {
        return type;
    }

    public void setType(@NonNull CatalogType type) {
        this.type = type;
    }

    @NonNull
    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(@NonNull Date lastChange) {
        this.lastChange = lastChange;
    }
}
