package ru.gastroafisha.MyApps.Model.Item;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "favorites", primaryKeys = {"orgIdF", "catalogIdF", "idF"})
public class FavoriteModel {

    @NonNull @ColumnInfo(name = "orgIdF") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "catalogIdF") // SQLite
    private Integer catalogId;

    @NonNull @ColumnInfo(name = "idF") // SQLite
    private Integer id;

    @NonNull
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull Integer orgId) {
        this.orgId = orgId;
    }

    @NonNull
    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(@NonNull Integer catalogId) {
        this.catalogId = catalogId;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
