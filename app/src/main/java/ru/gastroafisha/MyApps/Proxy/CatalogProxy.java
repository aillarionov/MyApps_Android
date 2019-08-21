package ru.gastroafisha.MyApps.Proxy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.gastroafisha.MyApps.Model.Catalog.CatalogType;


/**
 * Created by alex on 22.12.2017.
 */

public class CatalogProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("type") @Expose // GSON
    private CatalogType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CatalogType getType() {
        return type;
    }

    public void setType(CatalogType type) {
        this.type = type;
    }

}
