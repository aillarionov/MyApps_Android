package ru.gastroafisha.MyApps.Model.Form;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "forms", primaryKeys = {"orgId", "id"})
public class FormModel {

    @NonNull @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "id") // SQLite
    private Integer id;

    @NonNull @ColumnInfo(name = "name") // SQLite
    private String name;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
