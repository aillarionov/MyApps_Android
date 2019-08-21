package ru.gastroafisha.MyApps.Model.Menu;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import ru.gastroafisha.MyApps.Model.StringMap;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "menuitems", primaryKeys = {"orgId", "id"})
public class MenuItemModel {

    @NonNull @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "id") // SQLite
    private Integer id;

    @ColumnInfo(name = "name") // SQLite
    private String name;

    @ColumnInfo(name = "type") // SQLite
    private MenuItemType type;

    @ColumnInfo(name = "icon") // SQLite
    private String icon;

    @ColumnInfo(name = "params") // SQLite
    private StringMap params;

    @ColumnInfo(name = "order_") // SQLite
    private Integer order;

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

    public void setName(String name) {
        this.name = name;
    }

    public MenuItemType getType() {
        return type;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public StringMap getParams() {
        return params;
    }

    public void setParams(StringMap params) {
        this.params = params;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
