package ru.gastroafisha.MyApps.Model.Catalog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 30.12.2017.
 */

public enum CatalogType {
    @SerializedName("item")
    Item(1),

    @SerializedName("member")
    Member(2),

    @SerializedName("image")
    Image(3),

    @SerializedName("news")
    News(4);


    private int code;

    CatalogType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CatalogType getByCode(int code) {

        for (CatalogType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
