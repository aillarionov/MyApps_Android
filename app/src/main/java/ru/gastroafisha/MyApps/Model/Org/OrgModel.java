package ru.gastroafisha.MyApps.Model.Org;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ru.gastroafisha.MyApps.Model.ImageModel;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "orgs")
public class OrgModel {

    @PrimaryKey @ColumnInfo(name = "id") // SQLite
    private Integer id;

    @ColumnInfo(name = "name") // SQLite
    private String name;

    @ColumnInfo(name = "logo") // SQLite
    private ImageModel logo;

    @ColumnInfo(name = "city") // SQLite
    private CityModel city;

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

    public ImageModel getLogo() {
        return logo;
    }

    public void setLogo(ImageModel logo) {
        this.logo = logo;
    }

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }
}
